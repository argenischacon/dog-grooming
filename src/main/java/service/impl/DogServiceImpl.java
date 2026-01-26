package service.impl;

import dao.DogDAO;
import dao.impl.DogDAOimpl;
import dto.dog.DogDetailDto;
import dto.dog.DogFormDto;
import dto.dog.DogListDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import jpa.JpaUtil;
import mapper.DogMapper;
import model.Dog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.DogService;

import java.util.List;

public class DogServiceImpl implements DogService {

    private final DogDAO dogDAO;
    private final DogMapper dogMapper;
    private static final Logger logger = LoggerFactory.getLogger(DogServiceImpl.class);

    public DogServiceImpl() {
        this.dogDAO = new DogDAOimpl();
        this.dogMapper = DogMapper.INSTANCE;
    }

    @Override
    public DogDetailDto create(DogFormDto dto) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            logger.info("Creating dog");
            tx.begin();

            Dog dog = dogMapper.toEntity(dto);
            dogDAO.create(dog, em);

            tx.commit();
            logger.info("Dog created successfully");
            return dogMapper.toDetailDto(dog);

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

            tx.commit();
            logger.info("Dog updated successfully");
            return dogMapper.toDetailDto(dog);

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
            return dogMapper.toDetailDto(dog);

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
}
