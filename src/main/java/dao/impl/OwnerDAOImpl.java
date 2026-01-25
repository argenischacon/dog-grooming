package dao.impl;

import dao.OwnerDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Owner;

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
}
