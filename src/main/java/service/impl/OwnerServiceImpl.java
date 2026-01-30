package service.impl;

import dao.OwnerDAO;
import dao.impl.OwnerDAOImpl;
import dto.owner.OwnerListDto;
import dto.owner.OwnerDetailDto;
import dto.owner.OwnerFormDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import jpa.JpaUtil;
import mapper.OwnerMapper;
import model.Owner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.OwnerService;
import service.exception.DuplicateDniException;
import service.exception.OwnerWithDogsException;

import java.util.List;

public class OwnerServiceImpl implements OwnerService {
    private final OwnerDAO ownerDAO;
    private final OwnerMapper ownerMapper;
    private static final Logger logger = LoggerFactory.getLogger(OwnerServiceImpl.class);

    public OwnerServiceImpl() {
        this.ownerDAO = new OwnerDAOImpl();
        this.ownerMapper = OwnerMapper.INSTANCE;
    }

    @Override
    public OwnerDetailDto create(OwnerFormDto dto) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            logger.info("Creating owner");
            tx.begin();

            if(ownerDAO.existsByDni(dto.getDni(), em)){
                throw new DuplicateDniException("Un dueño con el DNI '" + dto.getDni() + "' ya existe");
            }

            Owner owner = ownerMapper.toEntity(dto);
            ownerDAO.create(owner, em);

            tx.commit();
            logger.info("Owner created successfully");
            return ownerMapper.toDetailDto(owner);

        } catch (Exception e) {
            logger.error("Error creating owner", e);
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public OwnerDetailDto update(Long id, OwnerFormDto dto) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            logger.info("Updating owner with id {}", id);
            tx.begin();

            Owner owner = ownerDAO.findById(id, em)
                    .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

            if(!owner.getDni().equals(dto.getDni()) && ownerDAO.existsByDni(dto.getDni(), em)){
                throw new DuplicateDniException("Un dueño con el DNI '" + dto.getDni() + "' ya existe");
            }

            ownerMapper.updateFromDto(dto, owner);

            tx.commit();
            logger.info("Owner updated successfully");
            return ownerMapper.toDetailDto(owner);

        } catch (Exception e) {
            logger.error("Error updating owner", e);
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public OwnerDetailDto findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            logger.info("Finding owner by id {}", id);

            Owner owner = ownerDAO.findById(id, em)
                    .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

            logger.info("Owner found");
            return ownerMapper.toDetailDto(owner);

        } finally {
            em.close();
        }
    }

    @Override
    public List<OwnerListDto> list(int page, int size) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            logger.info("Listing owners (page {}, size {})", page, size);

            List<Owner> owners = ownerDAO.findAll(page, size, em);
            return ownerMapper.toListDto(owners);

        } finally {
            em.close();
        }
    }

    @Override
    public long count() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return ownerDAO.count(em);
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            logger.info("Deleting owner with id {}", id);
            tx.begin();

            Owner owner = ownerDAO.findById(id, em)
                    .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

            if(!owner.getDogs().isEmpty()){
                throw new OwnerWithDogsException("No se puede eliminar dueño con perros asociados");
            }

            ownerDAO.delete(owner, em);

            tx.commit();
            logger.info("Owner deleted successfully");

        } catch (Exception e) {
            logger.error("Error deleting owner", e);
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
