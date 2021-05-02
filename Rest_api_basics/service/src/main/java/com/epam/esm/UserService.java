package com.epam.esm;

import com.epam.esm.dto.UserDTO;

import java.util.List;

/**
 * Interface describes the service for working with UserDTO.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public interface UserService {

    /**
     * Accesses the corresponding DAO method to create a new User object.
     *
     * @param userDTO object with User data.
     * @return created object with User data.
     */
    UserDTO create(UserDTO userDTO);

    /**
     * Accesses the corresponding DAO method to find User object with specific id.
     *
     * @param id User id.
     * @return object with User data.
     */
    UserDTO finById(Integer id);

    /**
     * Accesses the corresponding DAO method to find all Users.
     *
     * @param pageNumber number of page.
     * @param size number of Users on page.
     * @return List of objects with User data.
     */
    List<UserDTO> findAll(Integer pageNumber, Integer size);

    /**
     * Calculates the total number of pages required to display all Users.
     *
     * @return the total number of pages required to display all Users.
     */
    Integer findNumberPagesForAllUsers(Integer size);
}
