package com.epam.esm.dao.impl;

import com.epam.esm.constant.ParamNameConstant;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.query.GiftCertificateParam;
import com.epam.esm.dao.query.QueryAndParam;
import com.epam.esm.dao.query.builder.GiftCertificateQueryBuilder;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Class implements interface GiftCertificateDAO. Describes the interaction with the database
 * for operating with objects of the GiftCertificate type.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
@Repository
public class GiftCertificateDAOImpl implements GiftCertificateDAO {

    @PersistenceContext
    private EntityManager em;

    private static final String FIND_CERTIFICATE_BY_ID = "SELECT DISTINCT e FROM GiftCertificate e WHERE e.id = :id";
    private static final String DELETE_CERTIFICATE = "DELETE FROM GiftCertificate e WHERE e.id = :id";
    private static final String FIND_ALL_CERTIFICATES = "SELECT DISTINCT e FROM GiftCertificate e";
    private static final String FIND_CERTIFICATES_BY_TAG_NAME = "SELECT DISTINCT e FROM GiftCertificate e " +
            "INNER JOIN e.tagList c WHERE c.name = :name";

    /**
     * The index of the first item in the list.
     */
    private static final int FIRST_ELEMENT_INDEX = 0;

    /**
     * Adds new object GiftCertificate to database.
     *
     * @param giftCertificate object of the GiftCertificate type.
     * @return GiftCertificate entity.
     */
    @Override
    public GiftCertificate save(GiftCertificate giftCertificate) {
        em.persist(giftCertificate);
        return find(giftCertificate.getId()).get();
    }

    /**
     * Returns GiftCertificate with specific id.
     *
     * @param id GiftCertificate id.
     * @return Optional of GiftCertificate entity stored in the database.
     */
    @Override
    public Optional<GiftCertificate> find(Integer id) {
        List<GiftCertificate> listGiftCertificate = em.createQuery(FIND_CERTIFICATE_BY_ID, GiftCertificate.class).
                setParameter(ParamNameConstant.ID, id).getResultList();
        return listGiftCertificate.isEmpty() ? Optional.empty()
                : Optional.of(listGiftCertificate.get(FIRST_ELEMENT_INDEX));
    }

    /**
     * Deletes GiftCertificate with specific id from database.
     *
     * @param id GiftCertificate id.
     */
    @Override
    public void delete(Integer id) {
        em.createQuery(DELETE_CERTIFICATE).setParameter(ParamNameConstant.ID, id).executeUpdate();
    }

    /**
     * Returns all GiftCertificates stored in the database.
     *
     * @return all GiftCertificate stored in the database.
     */
    @Override
    @Transactional
    public List<GiftCertificate> findAll() {
        return em.createQuery(FIND_ALL_CERTIFICATES, GiftCertificate.class).getResultList();
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
     * @param giftCertificateParam special object containing params.
     * @return list of GiftCertificates.
     */
    @Override
    public List<GiftCertificate> findByParam(GiftCertificateParam giftCertificateParam) {
        QueryAndParam queryAndParam = GiftCertificateQueryBuilder.getInstance().buildGetQuery(giftCertificateParam);
        Query query = em.createQuery(queryAndParam.getQuery(), GiftCertificate.class);
        if (!queryAndParam.getParams().isEmpty()) {
            for (Map.Entry<String, Object> entry : queryAndParam.getParams().entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return query.getResultList();
    }
}
