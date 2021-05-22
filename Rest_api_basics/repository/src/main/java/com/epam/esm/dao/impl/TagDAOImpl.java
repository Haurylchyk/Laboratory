package com.epam.esm.dao.impl;

import com.epam.esm.constant.ParamNameConstant;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Class implements interface TagDAO. Describes the interaction with the database
 * for operating with objects of the Tag type.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
@Repository
public class TagDAOImpl extends EntityDAOImpl<Tag> implements TagDAO {

    private static final String FIND_TAG_BY_NAME = "SELECT DISTINCT e FROM Tag e WHERE e.name = :name";
    private static final String FIND_BY_CERTIFICATE_ID = "SELECT DISTINCT e FROM Tag e INNER JOIN e.certificates c WHERE c.id = :id";
    private static final String FIND_MOST_WIDELY_USED_TAG = "SELECT t FROM Order e INNER JOIN e.giftCertificateList c " +
            "INNER JOIN c.tagList t INNER JOIN e.user u WHERE u.id = :id GROUP BY t.name ORDER BY COUNT(t) DESC ";

    public TagDAOImpl() {
        super(Tag.class);
    }

    /**
     * The index of the first item in the list.
     */
    private static final int FIRST_ELEMENT_INDEX = 0;

    /**
     * Returns Tag with specific name.
     *
     * @param name Tag name.
     * @return Optional of Tag entity stored in the database.
     */
    @Override
    public Optional<Tag> findByName(String name) {
        List<Tag> tagList = em.createQuery(FIND_TAG_BY_NAME, Tag.class).
                setParameter(ParamNameConstant.NAME, name).getResultList();
        return tagList.isEmpty() ? Optional.empty() : Optional.of(tagList.get(FIRST_ELEMENT_INDEX));
    }

    /**
     * Returns list of Tags of GiftCertificate with specific id.
     *
     * @param id GiftCertificate id.
     * @return List of Tags.
     */
    @Override
    public List<Tag> findByGiftCertificateId(Integer id) {
        return em.createQuery(FIND_BY_CERTIFICATE_ID, Tag.class).
                setParameter(ParamNameConstant.ID, id).getResultList();
    }

    /**
     * Returns the most widely used tag for
     * the user with with specific id.
     *
     * @return the most widely used tag for
     * the user with with specific id.
     */
    public Tag findMostWidelyUsedByUserId(Integer id) {
        List<Tag> tagList = em.createQuery(FIND_MOST_WIDELY_USED_TAG, Tag.class).
                setParameter(ParamNameConstant.ID, id).getResultList();
        return tagList.get(FIRST_ELEMENT_INDEX);
    }
}
