package com.epam.esm.dao.query.sort;

import com.epam.esm.dao.query.parameter.GiftCertificateParam;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificate_;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public class OrderBy {
    public Order toOrderBy(Root<GiftCertificate> root, CriteriaBuilder cb, GiftCertificateParam param) {
        Order orderBy = null;
        String columnName = null;
        SortType sortType = param.getSortType();
        SortOrder sortOrder = param.getSortOrder();
        switch (sortType) {
            case NAME:
                columnName = GiftCertificate_.NAME;
                break;
            case DATE:
                columnName = GiftCertificate_.LAST_UPDATE_DATE;
                break;
        }
        switch (sortOrder) {
            case ASC:
                orderBy = cb.asc(root.get(columnName));
                break;
            case DESC:
                orderBy = cb.desc(root.get(columnName));
        }
        return orderBy;
    }
}
