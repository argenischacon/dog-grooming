package com.argenischacon.dao.impl;

import com.argenischacon.dao.OwnerDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import com.argenischacon.model.Owner;

import java.util.List;
import java.util.Optional;

public class OwnerDAOImpl implements OwnerDAO {
    @Override
    public Owner create(Owner owner, EntityManager em) {
        em.persist(owner);
        return owner;
    }

    @Override
    public Optional<Owner> findById(Long id, EntityManager em) {
        return Optional.ofNullable(em.find(Owner.class, id));
    }

    @Override
    public List<Owner> findAll(int offset, int limit, EntityManager em) {
        String jpql = "select o from Owner o order by o.id desc";
        TypedQuery<Owner> q = em.createQuery(jpql, Owner.class);
        q.setFirstResult(Math.max(0, offset));
        q.setMaxResults(Math.max(1, limit));
        return q.getResultList();
    }

    @Override
    public long count(EntityManager em) {
        String jpql = "select count(o) from Owner o";
        return em.createQuery(jpql, Long.class).getSingleResult();
    }

    @Override
    public void delete(Owner owner, EntityManager em) {
        Owner attached = em.contains(owner)? owner : em.merge(owner);
        em.remove(attached);
    }

    @Override
    public boolean existsByDni(String dni, EntityManager em) {
        String jpql = "select count(o) from Owner o where o.dni = :dni";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("dni", dni)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public List<Owner> search(String text, int offset, int limit, EntityManager em) {
        String jpql = "select o from Owner o where " +
                "lower(o.name) like :text or " +
                "lower(o.lastname) like :text or " +
                "lower(o.dni) like :text or " +
                "lower(o.phone) like :text " +
                "order by o.id desc";
        TypedQuery<Owner> q = em.createQuery(jpql, Owner.class);
        q.setParameter("text", "%" + text.toLowerCase() + "%");
        q.setFirstResult(Math.max(0, offset));
        q.setMaxResults(Math.max(1, limit));
        return q.getResultList();
    }

    @Override
    public long countSearch(String text, EntityManager em) {
        String jpql = "select count(o) from Owner o where " +
                "lower(o.name) like :text or " +
                "lower(o.lastname) like :text or " +
                "lower(o.dni) like :text or " +
                "lower(o.phone) like :text";
        return em.createQuery(jpql, Long.class)
                .setParameter("text", "%" + text.toLowerCase() + "%")
                .getSingleResult();
    }

    @Override
    public long countByIdGreaterThan(Long id, EntityManager em) {
        String jpql = "select count(o) FROM Owner o where o.id > :id";
        return em.createQuery(jpql, Long.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
