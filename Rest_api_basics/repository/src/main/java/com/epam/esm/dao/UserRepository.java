package com.epam.esm.dao;

import com.epam.esm.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Interface describes the interaction with the database
 * for operating with objects of the User type.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Returns User with specific login.
     *
     * @param login User login.
     * @return Optional of User entity stored in the database.
     */
    List<User> findByLogin(String login);

    /**
     * Returns the user with the highest order amount.
     *
     * @return the user with the highest order amount.
     */
    @Query("SELECT u FROM Order o JOIN o.user u GROUP BY u ORDER BY SUM(o.cost) DESC")
    Page<User> findUserWithTopOrders(Pageable pageable);
}
