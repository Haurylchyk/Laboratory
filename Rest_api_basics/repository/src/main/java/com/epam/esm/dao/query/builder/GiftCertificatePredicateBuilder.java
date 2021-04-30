package com.epam.esm.dao.query.builder;

import com.epam.esm.dao.query.parameter.GiftCertificateParam;
import com.epam.esm.dao.query.builder.impl.*;
import com.epam.esm.entity.GiftCertificate;

import java.util.ArrayList;
import java.util.List;

/**
 * Class is used to convert parameters obtained
 * from a HTTP request to an SQL query.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public class GiftCertificatePredicateBuilder {

    private static final GiftCertificatePredicateBuilder instance = new GiftCertificatePredicateBuilder();

    private GiftCertificatePredicateBuilder() {
    }

    /**
     * Returns instance of this class.
     *
     * @return Instance of GiftCertificateQueryBuilder.
     */
    public static GiftCertificatePredicateBuilder getInstance() {
        return instance;
    }

    /**
     * Converts parameters from request into set of conditions for query.
     *
     * @param param object with parameters from request.
     * @return object, that contains set of conditions for query.
     */
    public PredicateBuilder build(GiftCertificateParam param) {
        List<PredicateBuilder<GiftCertificate, GiftCertificateParam>> predicateList = new ArrayList<>();
        if (param.getName() != null) {
            predicateList.add(new GiftCertificateNamePredicateBuilder());
        }
        if (param.getDescription() != null) {
            predicateList.add(new GiftCertificateDescriptionPredicateBuilder());
        }
        if (param.getPriceFilterList() != null) {
            predicateList.add(new GiftCertificatePricePredicateBuilder());
        }
        if (param.getDurationFilterList() != null) {
            predicateList.add(new GiftCertificateDurationPredicateBuilder());
        }
        if (param.getTagNameList() != null) {
            predicateList.add(new GiftCertificateTagNamePredicateBuilder());
        }
        return new CommonPredicateBuilder(predicateList);
    }
}
