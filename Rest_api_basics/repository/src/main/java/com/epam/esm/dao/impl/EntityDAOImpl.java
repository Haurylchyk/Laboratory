package com.epam.esm.dao.impl;

import com.epam.esm.dao.EntityDAO;
import com.epam.esm.entity.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public class EntityDAOImpl<E extends BaseEntity> implements EntityDAO<E> {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public E save(E entity) {
        em.persist(entity);
        return find(entity.getId()).get();
    }

    @Override
    public Optional<E> find(Integer id) {
        E entity = em.find(getType(), id);
        return Optional.ofNullable(entity);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        E entity = em.find(getType(), id);
        em.remove(entity);
    }

    @Override
    public List<E> findAll(Integer pageNumber, Integer size) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<E> query = cb.createQuery(getType());
        Root<E> root = query.from(getType());
        query.select(root);
        return em.createQuery(query).setMaxResults(size)
                .setFirstResult(size * (pageNumber - 1)).getResultList();
    }

    @Override
    public Integer countAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery();
        query.select(cb.count(query.from(getType())));
        Long number =(Long) em.createQuery(query).getSingleResult();
        return number.intValue();
    }

    private Class <E> getType() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class <E>) type.getActualTypeArguments () [0];
    }
}
