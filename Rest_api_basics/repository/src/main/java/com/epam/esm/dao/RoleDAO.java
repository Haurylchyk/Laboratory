package com.epam.esm.dao;

import com.epam.esm.entity.Role;

/**
 * Interface describes the interaction with the database
 * for operating with objects of the Role type.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public interface RoleDAO {

    /**
     * Returns Role with specific name.
     *
     * @param name Role name.
     * @return Optional of Role entity stored in the database.
     */
    Role findByName(String name);
}