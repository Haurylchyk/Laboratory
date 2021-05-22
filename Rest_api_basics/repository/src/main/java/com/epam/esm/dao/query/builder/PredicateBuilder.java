package com.epam.esm.dao.query.builder;

import javax.persistence.criteria.CriteriaBuilder;
import  javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Interface is designed to return a Predicate
 * that can be used to query the database.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public interface PredicateBuilder<T,P> {
    Predicate toPredicate(Root<T> root, CriteriaBuilder cb, P p);
}
