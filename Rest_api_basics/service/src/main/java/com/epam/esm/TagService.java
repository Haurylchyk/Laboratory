package com.epam.esm;

import com.epam.esm.dto.TagDTO;

import java.util.List;

/**
 * Interface describes the service for working with TagDTO.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public interface TagService {

    /**
     * Accesses the corresponding DAO method to create a new Tag object.
     *
     * @param tagDTO object with Tag data.
     * @return created object with Tag data.
     */
    TagDTO createTag(TagDTO tagDTO);

    /**
     * Accesses the corresponding DAO method to get Tag object with specific id.
     *
     * @param id Tag id.
     * @return object with Tag data.
     */
    TagDTO getTagById(Integer id);

    /**
     * Accesses the corresponding DAO method to delete Tag object with specific id.
     *
     * @param id Tag id.
     */
    void deleteTag(Integer id);

    /**
     * Accesses the corresponding DAO method to get all Tags.
     *
     * @return List of objects with Tag data.
     */
    List<TagDTO> getAllTags();

    /**
     * Accesses the corresponding DAO method to get Tag object with specific name.
     *
     * @param name Tag name.
     * @return object with Tag data.
     */
    TagDTO getTagByName(String name);

    /**
     * Accesses the corresponding DAO method to get List of all Tags
     * that linked with specific GiftCertificate.
     *
     * @param id GiftCertificate id.
     * @return List of objects with tag data.
     */
    List<TagDTO> getTagsByGiftCertificateId(Integer id);
}
