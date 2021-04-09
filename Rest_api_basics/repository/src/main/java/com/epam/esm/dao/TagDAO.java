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
     * @return all Tags stored in the database.
     */
    List<Tag> readAllTags();

    /**
     * Returns Tag with specific name.
     *
     * @param name Tag name.
     * @return Optional of Tag entity stored in the database.
     */
    Optional<Tag> readTagByName(String name);

    /**
     * Returns list of Tags of GiftCertificate with specific id.
     *
     * @param id GiftCertificate id.
     * @return List of Tags.
     */
    List<Tag> readTagsByGiftCertificateId(Integer id);

}
