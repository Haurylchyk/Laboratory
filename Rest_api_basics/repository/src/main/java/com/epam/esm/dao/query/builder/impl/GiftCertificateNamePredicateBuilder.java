package com.epam.esm.dao.query.builder.impl;

import com.epam.esm.dao.query.parameter.GiftCertificateParam;
import com.epam.esm.dao.query.builder.PredicateBuilder;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificate_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Class is designed to return a Predicate
 * for the name of the GiftCertificate.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public class GiftCertificateNamePredicateBuilder implements PredicateBuilder<GiftCertificate, GiftCertificateParam> {

    private static final String SIGN = "%";

    @Override
    public Predicate toPredicate(Root<GiftCertificate> root, CriteriaBuilder cb, GiftCertificateParam param) {
        return cb.like(root.get(GiftCertificate_.NAME),
                SIGN + param.getName() + SIGN);
    }
}
