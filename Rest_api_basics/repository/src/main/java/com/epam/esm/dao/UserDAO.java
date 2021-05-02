package com.epam.esm.dao;

import com.epam.esm.entity.User;

import java.util.List;
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
     * Returns all Users stored in the database.
     *
     * @param pageNumber number of page.
     * @param size number of Users on page.
     * @return all Users stored in the database.
     */
    List<User> findAll(Integer pageNumber, Integer size);

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

    /**
     * Returns the number of all Users in the database.
     *
     * @return the number of all Users in the database.
     */
    Integer findTotalNumberUsers();
}
