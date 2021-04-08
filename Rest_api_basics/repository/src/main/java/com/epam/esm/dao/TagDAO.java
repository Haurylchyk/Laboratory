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
public interface TagDAO {

    /**
     * Adds new Tag to database.
     *
     * @param name Tag name.
     * @return Tag entity.
     */
    Tag createTag(String name);

    /**
     * Returns Tag with specific id.
     *
     * @param id Tag id.
     * @return Optional of Tag entity stored in the database.
     */
    Optional<Tag> getTagById(Integer id);

    /**
     * Deletes Tag with specific id from database.
     *
     * @param id Tag id.
     */
    void deleteTag(Integer id);

    /**
     * Returns all Tags stored in the database.
     *
     * @return all Tags stored in the database.
     */
    List<Tag> getAllTags();

    /**
     * Returns Tag with specific name.
     *
     * @param name Tag name.
     * @return Optional of Tag entity stored in the database.
     */
    Optional<Tag> getTagByName(String name);

    /**
     * Returns list of Tags of GiftCertificate with specific id.
     *
     * @param id GiftCertificate id.
     * @return List of Tags.
     */
    List<Tag> getTagsByGiftCertificateId(Integer id);



}
