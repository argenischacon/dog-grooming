package dao.impl;

import dao.DogDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Dog;

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
        String jpql = "select d from dogs d order by d.id desc";
        TypedQuery<Dog> q = em.createQuery(jpql, Dog.class);
        q.setFirstResult(Math.max(0, offset));
        q.setMaxResults(Math.max(1, limit));
        return q.getResultList();
    }

    @Override
    public long count(EntityManager em) {
        String jpql = "select count(d) from dogs d";
        return em.createQuery(jpql, Long.class).getSingleResult();
    }

    @Override
    public void delete(Dog dog, EntityManager em) {
        Dog attached = em.contains(dog)? dog : em.merge(dog);
        em.remove(attached);
    }
}
