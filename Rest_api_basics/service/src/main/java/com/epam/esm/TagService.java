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
    TagDTO create(TagDTO tagDTO);

    /**
     * Accesses the corresponding DAO method to get Tag object with specific id.
     *
     * @param id Tag id.
     * @return object with Tag data.
     */
    TagDTO finById(Integer id);

    /**
     * Accesses the corresponding DAO method to delete Tag object with specific id.
     *
     * @param id Tag id.
     */
    void delete(Integer id);

    /**
     * Accesses the corresponding DAO method to get all Tags.
     *
     * @return List of objects with Tag data.
     */
    List<TagDTO> findAll();
}
