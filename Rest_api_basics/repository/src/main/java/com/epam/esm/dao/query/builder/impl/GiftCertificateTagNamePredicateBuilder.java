package com.epam.esm.dao.query.builder.impl;

import com.epam.esm.dao.query.parameter.GiftCertificateParam;
import com.epam.esm.dao.query.builder.PredicateBuilder;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificate_;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.Tag_;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class is designed to return a Predicate
 * for the tag name list of the GiftCertificate.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public class GiftCertificateTagNamePredicateBuilder implements PredicateBuilder<GiftCertificate, GiftCertificateParam> {

    @Override
    public Predicate toPredicate(Root<GiftCertificate> root, CriteriaBuilder cb, GiftCertificateParam param) {
        List<String> tagNameList = param.getTagNameList().stream().distinct().collect(Collectors.toList());
        List<Predicate> predicateList = new ArrayList<>();

        ListJoin<Tag, GiftCertificate> tagJoin = root.joinList(GiftCertificate_.TAG_LIST);
        Path<Tag> tagList = tagJoin.get(Tag_.NAME);
        tagNameList.forEach(tag -> predicateList.add(cb.equal(tagList, tag)));

        return cb.and(predicateList.toArray(new Predicate[0]));
    }
}
