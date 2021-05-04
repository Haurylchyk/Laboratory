package com.epam.esm.dao;

import com.epam.esm.entity.User;

import java.util.Optional;

/**
 * Interface describes the interaction with the database
 * for operating with objects of the User type.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public interface UserDAO extends EntityDAO<User> {

    /**
     * Returns User with specific login.
     *
     * @param login User login.
     * @return Optional of User entity stored in the database.
     */
    Optional<User> findByLogin(String login);

    /**
     * Returns the user with the highest order amount.
     *
     * @return the user with the highest order amount.
     */
    User findUserWithTopOrders();

}
