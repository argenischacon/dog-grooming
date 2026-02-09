package com.argenischacon.dao;

import jakarta.persistence.EntityManager;
import com.argenischacon.model.Dog;

import java.util.List;
import java.util.Optional;

public interface DogDAO {
    Dog create(Dog dog, EntityManager em);

    Optional<Dog> findById(Long id, EntityManager em);

    List<Dog> findAll(int offset, int limit, EntityManager em);

    long count(EntityManager em);

    void delete(Dog dog, EntityManager em);

    List<Dog> search(String text, int offset, int limit, EntityManager em);

    long countSearch(String text, EntityManager em);
}