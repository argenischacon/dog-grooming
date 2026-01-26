package dao;

import jakarta.persistence.EntityManager;
import model.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerDAO {
    Owner create(Owner owner, EntityManager em);

    Optional<Owner> findById(Long id, EntityManager em);

    List<Owner> findAll(int offset, int limit, EntityManager em);

    long count(EntityManager em);

    void delete(Owner owner, EntityManager em);

    boolean existsByDni(String dni, EntityManager em);
}
