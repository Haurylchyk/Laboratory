package com.epam.esm.dao;

import com.epam.esm.entity.BaseEntity;

import java.util.Optional;

/**
 * Interface describes the interaction with the database
 * for operating with entities (objects of different types).
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public interface EntityDAO<T extends BaseEntity> {

    /**
     * Creates entity into database.
     *
     * @param entity object of type T.
     *
     * @return id of the inserted record.
     */
    T save(T entity);

    /**
     * Returns entity with specific id.
     *
     * @param id entity id.
     *
     * @return Optional of T entity stored in the database.
     */
    Optional<T> find(Integer id);

    /**
     * Deletes entity (object of type T) from database.
     *
     * @param id entity id.
     */
    void delete(Integer id);
}

