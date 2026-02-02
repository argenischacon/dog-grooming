package com.argenischacon.service.impl;

import com.argenischacon.dao.DogDAO;
import com.argenischacon.dao.OwnerDAO;
import com.argenischacon.dao.impl.DogDAOimpl;
import com.argenischacon.dao.impl.OwnerDAOImpl;
import com.argenischacon.dto.dog.DogDetailDto;
import com.argenischacon.dto.dog.DogFormDto;
import com.argenischacon.dto.dog.DogListDto;
import com.argenischacon.dto.owner.OwnerListDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import com.argenischacon.jpa.JpaUtil;
import com.argenischacon.mapper.DogMapper;
import com.argenischacon.mapper.OwnerMapper;
import com.argenischacon.model.Dog;
import com.argenischacon.model.Owner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.argenischacon.service.DogService;

import java.util.List;
import java.util.Objects;

public class DogServiceImpl implements DogService {

    private final DogDAO dogDAO;
    private final DogMapper dogMapper;
    private final OwnerMapper ownerMapper;
    private final OwnerDAO ownerDAO;
    private static final Logger logger = LoggerFactory.getLogger(DogServiceImpl.class);

    public DogServiceImpl() {
        this.dogDAO = new DogDAOimpl();
        this.dogMapper = DogMapper.INSTANCE;
        this.ownerMapper = OwnerMapper.INSTANCE;
        this.ownerDAO = new OwnerDAOImpl();
    }

    @Override
    public DogDetailDto create(DogFormDto dto) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            logger.info("Creating dog");
            tx.begin();

            Owner owner = ownerDAO.findById(dto.getOwnerId(), em).orElseThrow(() ->
                    new EntityNotFoundException("Owner not found"));

            Dog dog = dogMapper.toEntity(dto);
            owner.addDog(dog);

            dogDAO.create(dog, em);

            tx.commit();
            logger.info("Dog created successfully");
            return mapDogWithOwner(dog);

        } catch (Exception e) {
            logger.error("Error creating dog", e);
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public DogDetailDto update(Long id, DogFormDto dto) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            logger.info("Updating dog with id {}", id);
            tx.begin();

            Dog dog = dogDAO.findById(id, em)
                    .orElseThrow(() -> new EntityNotFoundException("Dog not found"));

            dogMapper.updateDogFromDto(dto, dog);

            if(!Objects.equals(dto.getOwnerId(), dog.getOwner().getId())){
                Owner oldOwner = dog.getOwner();
                Owner newOwner = ownerDAO.findById(dto.getOwnerId(), em).orElseThrow(() ->
                        new EntityNotFoundException("Owner not found"));
                oldOwner.removeDog(dog);
                newOwner.addDog(dog);
            }

            tx.commit();
            logger.info("Dog updated successfully");
            return mapDogWithOwner(dog);

        } catch (Exception e) {
            logger.error("Error updating dog", e);
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public DogDetailDto findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            logger.info("Finding dog by id {}", id);

            Dog dog = dogDAO.findById(id, em)
                    .orElseThrow(() -> new EntityNotFoundException("Dog not found"));

            logger.info("Dog found");
            return mapDogWithOwner(dog);

        } finally {
            em.close();
        }
    }

    @Override
    public List<DogListDto> list(int page, int size) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            logger.info("Listing dogs (page {}, size {})", page, size);

            List<Dog> dogs = dogDAO.findAll(page, size, em);
            return dogMapper.toListDto(dogs);

        } finally {
            em.close();
        }
    }

    @Override
    public long count() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return dogDAO.count(em);
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            logger.info("Deleting dog with id {}", id);
            tx.begin();

            Dog dog = dogDAO.findById(id, em)
                    .orElseThrow(() -> new EntityNotFoundException("Dog not found"));

            Owner oldOwner = dog.getOwner();
            oldOwner.removeDog(dog);

            dogDAO.delete(dog, em);

            tx.commit();
            logger.info("Dog deleted successfully");

        } catch (Exception e) {
            logger.error("Error deleting dog", e);
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private DogDetailDto mapDogWithOwner(Dog dog) {
        DogDetailDto detailDto = dogMapper.toDetailDto(dog);
        OwnerListDto listDto = ownerMapper.toListDto(dog.getOwner());
        detailDto.setOwner(listDto);
        return detailDto;
    }
}
