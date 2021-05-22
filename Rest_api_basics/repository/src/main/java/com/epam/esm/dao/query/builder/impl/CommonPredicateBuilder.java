package com.epam.esm.dao.query.builder.impl;

import com.epam.esm.dao.query.builder.PredicateBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Class is designed to return a Predicate
 * obtained from several other Predicates.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public class CommonPredicateBuilder<T, P> implements PredicateBuilder<T, P> {
    private List<PredicateBuilder<T, P>> predicateBuilderList;

    public CommonPredicateBuilder(List<PredicateBuilder<T, P>> predicateBuilderList) {
        this.predicateBuilderList = predicateBuilderList;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaBuilder cb, P p) {
        List<Predicate> predicateList = new ArrayList<>();
        for (PredicateBuilder<T, P> predicateBuilder : predicateBuilderList) {
            predicateList.add(predicateBuilder.toPredicate(root, cb, p));
        }
        return cb.and(predicateList.toArray(new Predicate[0]));
    }
}
