package com.epam.esm.dao;

import com.epam.esm.entity.BaseEntity;

import java.util.List;
import java.util.Optional;

/**
 * Interface describes the interaction with the database
 * for operating with entities (objects of different types).
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public interface EntityDAO<E extends BaseEntity> {

    /**
     * Creates entity into database.
     *
     * @param entity object of type E.
     *
     * @return id of the inserted record.
     */
    E save(E entity);

    /**
     * Returns entity with specific id.
     *
     * @param id entity id.
     *
     * @return Optional of E entity stored in the database.
     */
    Optional<E> find(Integer id);

    /**
     * Deletes entity (object of type E) from database.
     *
     * @param id entity id.
     */
    void delete(Integer id);

    /**
     * Returns all entity stored in the database.
     *
     * @param pageNumber number of page.
     * @param size number of entities on page.
     * @return all entities stored in the database.
     */
    List<E> findAll(Integer pageNumber, Integer size);

    /**
     * Returns the number of all entities in the database.
     *
     * @return the number of all entities in the database.
     */
    Integer countAll();
}

