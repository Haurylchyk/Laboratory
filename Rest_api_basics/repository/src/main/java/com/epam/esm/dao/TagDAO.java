package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * Interface describes the interaction with the database
 * for operating with objects of the Tag type.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public interface TagDAO extends EntityDAO<Tag> {

    /**
     * Returns all Tags stored in the database.
     *
     * @param pageNumber number of page.
     * @param size number of Tags on page.
     * @return all Tags stored in the database.
     */
    List<Tag> findAll(Integer pageNumber, Integer size);

    /**
     * Returns Tag with specific name.
     *
     * @param name Tag name.
     * @return Optional of Tag entity stored in the database.
     */
    Optional<Tag> findByName(String name);

    /**
     * Returns list of Tags of GiftCertificate with specific id.
     *
     * @param id GiftCertificate id.
     * @return List of Tags.
     */
    List<Tag> findByGiftCertificateId(Integer id);

    /**
     * Returns the most widely used tag for
     * the user with with specific.
     *
     * @return the most widely used tag for
     * the user with with specific.
     */
    Tag findMostWidelyUsedByUserId(Integer id);

    /**
     * Returns the number of all Tags in the database.
     *
     * @return the number of all Tags in the database.
     */
    Integer findTotalNumberTags();
}
