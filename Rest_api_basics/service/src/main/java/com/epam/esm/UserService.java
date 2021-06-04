package com.epam.esm;

import com.epam.esm.model.CreatingUserData;
import com.epam.esm.model.dto.AuthRequestDTO;
import com.epam.esm.model.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @param creatingUserData object containing the data required to create a User.
     * @return created object with User data.
     */
    UserDTO signUp(CreatingUserData creatingUserData);

    /**
     * Accesses the appropriate DAO method so that the authenticated User can log in.
     *
     * @param authRequestDTO object containing the data required to log in.
     * @return created object with User data.
     */
    UserDTO login(AuthRequestDTO authRequestDTO);

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
     * @param pageable object contains page number and page size..
     * @return List of objects with User data.
     */
    Page<UserDTO> findAll(Pageable pageable);
}
