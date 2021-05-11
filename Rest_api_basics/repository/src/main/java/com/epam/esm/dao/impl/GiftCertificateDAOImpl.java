package com.epam.esm.dao.impl;

import com.epam.esm.constant.ParamNameConstant;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.query.builder.GiftCertificatePredicateBuilder;
import com.epam.esm.dao.query.builder.PredicateBuilder;
import com.epam.esm.dao.query.parameter.GiftCertificateParam;
import com.epam.esm.dao.query.sort.OrderBy;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Class implements interface GiftCertificateDAO. Describes the interaction with the database
 * for operating with objects of the GiftCertificate type.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
@Repository
public class GiftCertificateDAOImpl extends EntityDAOImpl<GiftCertificate> implements GiftCertificateDAO {

    private static final String FIND_CERTIFICATES_BY_TAG_NAME = "SELECT DISTINCT e FROM GiftCertificate e " +
            "INNER JOIN e.tagList c WHERE c.name = :name";

    public GiftCertificateDAOImpl() {
        super(GiftCertificate.class);
    }

    /**
     * Returns GiftCertificates that have Tag with specific name.
     *
     * @return list of GiftCertificates.
     */
    @Override
    public List<GiftCertificate> findByTagName(String name) {
        return em.createQuery(FIND_CERTIFICATES_BY_TAG_NAME, GiftCertificate.class).
                setParameter(ParamNameConstant.NAME, name).getResultList();
    }

    /**
     * Returns list of matching GiftCertificates.
     *
     * @param param special object containing params.
     * @return list of GiftCertificates.
     */
    @Override
    public List<GiftCertificate> findByParam(Integer page, Integer size, GiftCertificateParam param) {
        PredicateBuilder predicateBuilder = GiftCertificatePredicateBuilder.getInstance().build(param);

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);

        Predicate predicate = predicateBuilder.toPredicate(root, criteriaBuilder, param);
        criteriaQuery.where(predicate);

        if (param.getSortType() != null && param.getSortOrder() != null) {
            OrderBy orderBy = new OrderBy();
            Order order = orderBy.toOrderBy(root, criteriaBuilder, param);
            criteriaQuery.orderBy(order);
        }

        return em.createQuery(criteriaQuery).setMaxResults(size).setFirstResult(size * (page - 1)).getResultList();
    }
}
