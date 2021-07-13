package com.epam.esm;

import com.epam.esm.model.dto.TagDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * Accesses the corresponding DAO method to find Tag object with specific id.
     *
     * @param id Tag id.
     * @return object with Tag data.
     */
    TagDTO findById(Integer id);

    /**
     * Accesses the corresponding DAO method to delete Tag object with specific id.
     *
     * @param id Tag id.
     */
    void delete(Integer id);

    /**
     * Accesses the corresponding DAO method to find all Tags.
     *
     * @param pageable object contains page number and page size.
     * @return List of objects with Tag data.
     */
    Page<TagDTO> findAll(Pageable pageable);

    /**
     * Accesses the corresponding DAO method to find most widely used Tag for
     * the user with the highest cost of all orders.
     *
     * @return object with Tag data.
     */
    TagDTO findMostWidelyUsedOfTopOrderUser();
}
