package com.epam.esm.dao.query.builder.impl;

import com.epam.esm.dao.query.parameter.GiftCertificateParam;
import com.epam.esm.dao.query.filter.Filter;
import com.epam.esm.dao.query.builder.PredicateBuilder;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificate_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Class is designed to return a Predicate
 * for the duration of the GiftCertificate.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public class GiftCertificateDurationPredicateBuilder implements PredicateBuilder<GiftCertificate, GiftCertificateParam> {

    @Override
    public Predicate toPredicate(Root<GiftCertificate> root, CriteriaBuilder cb, GiftCertificateParam param) {
        List<Predicate> predicateList = new ArrayList<>();
        Path<GiftCertificate> path = root.get(GiftCertificate_.DURATION);

        for (Filter filter : param.getDurationFilterList()) {
            Predicate predicate = fromFilter(cb, path, filter);
            predicateList.add(predicate);
        }
        return cb.and(predicateList.toArray(new Predicate[0]));
    }

    private static Predicate fromFilter(CriteriaBuilder criteriaBuilder, Path path, Filter filter) {
        int value = filter.getValue();
        switch (filter.getType()) {
            case GT: {
                return criteriaBuilder.gt(path, value);
            }
            case LT: {
                return criteriaBuilder.lt(path, value);
            }
            default: {
                return criteriaBuilder.equal(path, value);
            }
        }
    }
}
