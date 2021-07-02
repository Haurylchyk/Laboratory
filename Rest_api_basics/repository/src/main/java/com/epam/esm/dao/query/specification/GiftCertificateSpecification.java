package com.epam.esm.dao.query.specification;

import com.epam.esm.dao.query.builder.GiftCertificatePredicateBuilder;
import com.epam.esm.dao.query.builder.PredicateBuilder;
import com.epam.esm.dao.query.parameter.GiftCertificateParam;
import com.epam.esm.dao.query.sort.OrderBy;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificate_;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.Tag_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class GiftCertificateSpecification {

    public static Specification<GiftCertificate> findByParam(
            GiftCertificateParam param) {

        return (Specification<GiftCertificate>) (root, query, cb) -> {
            PredicateBuilder predicateBuilder = GiftCertificatePredicateBuilder.getInstance().build(param);
            List<Predicate> predicateList = new ArrayList<>();
            predicateList.add(predicateBuilder.toPredicate(root, cb, param));

            if (param.getTagNameList() != null) {
                List<String> tagNameList = param.getTagNameList().stream().distinct().collect(Collectors.toList());
                Join<GiftCertificate, Tag> tagJoin = root.join(GiftCertificate_.TAG_LIST);
                CriteriaBuilder.In<String> stringIn = cb.in(tagJoin.get(Tag_.NAME));
                tagNameList.forEach(tagName -> stringIn.value(tagName));
                query.groupBy(root.get(GiftCertificate_.ID))
                        .having(cb.equal(cb.count(tagJoin.get(Tag_.NAME)), tagNameList.size()));
                tagNameList.forEach(tagName -> predicateList.add(stringIn));
            }

            if (param.getSortType() != null && param.getSortOrder() != null) {
                OrderBy orderBy = new OrderBy();
                Order order = orderBy.toOrderBy(root, cb, param);
                query.orderBy(order);
                query.orderBy(order);
            }
            return cb.and(predicateList.toArray(new Predicate[0]));
        };
    }
}
