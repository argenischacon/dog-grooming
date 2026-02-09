package com.argenischacon.dao.impl;

import com.argenischacon.dao.DogDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import com.argenischacon.model.Dog;

import java.util.List;
import java.util.Optional;

public class DogDAOimpl implements DogDAO {
    @Override
    public Dog create(Dog dog, EntityManager em) {
        em.persist(dog);
        return dog;
    }

    @Override
    public Optional<Dog> findById(Long id, EntityManager em) {
        return Optional.ofNullable(em.find(Dog.class, id));
    }

    @Override
    public List<Dog> findAll(int offset, int limit, EntityManager em) {
        String jpql = "select d from Dog d order by d.id desc";
        TypedQuery<Dog> q = em.createQuery(jpql, Dog.class);
        q.setFirstResult(Math.max(0, offset));
        q.setMaxResults(Math.max(1, limit));
        return q.getResultList();
    }

    @Override
    public long count(EntityManager em) {
        String jpql = "select count(d) from Dog d";
        return em.createQuery(jpql, Long.class).getSingleResult();
    }

    @Override
    public void delete(Dog dog, EntityManager em) {
        Dog attached = em.contains(dog)? dog : em.merge(dog);
        em.remove(attached);
    }

    @Override
    public List<Dog> search(String text, int offset, int limit, EntityManager em) {
        String jpql = "select d from Dog d join d.owner o where " +
                "lower(d.name) like :text or " +
                "lower(d.dogBreed) like :text or " +
                "lower(d.color) like :text or " +
                "lower(o.name) like :text or " +
                "lower(o.lastname) like :text or " +
                "lower(o.dni) like :text or " +
                "lower(o.phone) like :text " +
                "order by d.id desc";
        TypedQuery<Dog> q = em.createQuery(jpql, Dog.class);
        q.setParameter("text", "%" + text.toLowerCase() + "%");
        q.setFirstResult(Math.max(0, offset));
        q.setMaxResults(Math.max(1, limit));
        return q.getResultList();
    }

    @Override
    public long countSearch(String text, EntityManager em) {
        String jpql = "select count(d) from Dog d join d.owner o where " +
                "lower(d.name) like :text or " +
                "lower(d.dogBreed) like :text or " +
                "lower(o.name) like :text or " +
                "lower(d.color) like :text or " +
                "lower(o.lastname) like :text or " +
                "lower(o.dni) like :text or " +
                "lower(o.phone) like :text";
        return em.createQuery(jpql, Long.class)
                .setParameter("text", "%" + text.toLowerCase() + "%")
                .getSingleResult();
    }
}
