package com.epam.esm.impl;

import com.epam.esm.UserService;
import com.epam.esm.constant.ErrorCodeMessage;
import com.epam.esm.constant.RoleNameConstant;
import com.epam.esm.dao.RoleDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exception.impl.AuthException;
import com.epam.esm.exception.impl.EntityNotFoundException;
import com.epam.esm.exception.impl.ExistingUserException;
import com.epam.esm.exception.impl.NotExistingPageException;
import com.epam.esm.model.CreatingUserData;
import com.epam.esm.model.dto.AuthRequestDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.dto.mapper.UserDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Class implements interface UserService. Describes the service
 * for working with UserDTO.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    /**
     * Object of the UserDAO type.
     */
    private final UserDAO userDAO;

    /**
     * Object of the RoleDAO type.
     */
    private final RoleDAO roleDAO;

    /**
     * Object intended for encoding a password.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor with parameter.
     *
     * @param userDAO interface providing DAO methods.
     */
    @Autowired
    public UserServiceImpl(UserDAO userDAO, RoleDAO roleDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Accesses the corresponding DAO method to create a new User object.
     *
     * @param creatingUserData object containing the data required to create a User.
     * @return created object with User data.
     */
    @Override
    public UserDTO signUp(CreatingUserData creatingUserData) {
        Optional<User> userByLogin = userDAO.findByLogin(creatingUserData.getLogin());
        if (userByLogin.isPresent()) {
            throw new ExistingUserException(ErrorCodeMessage.ERROR_CODE_USER_EXISTS);
        }
        User user = new User();
        user.setName(creatingUserData.getName());
        user.setLogin(creatingUserData.getLogin());
        user.setPassword(passwordEncoder.encode(creatingUserData.getPassword()));
        Role role = roleDAO.findByName(RoleNameConstant.USER);
        user.setRole(role);
        User newUser = userDAO.save(user);
        return UserDTOMapper.convertToDTO(newUser);
    }

    /**
     * Accesses the appropriate DAO method so that the authenticated User can log in.
     *
     * @param authRequestDTO object containing the data required to log in.
     * @return created object with User data.
     */
    @Override
    public UserDTO login(AuthRequestDTO authRequestDTO) {
        String login = authRequestDTO.getLogin();
        User userByLogin = userDAO.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException(ErrorCodeMessage.ERROR_CODE_USER_NOT_FOUND));

        if (!passwordEncoder.matches(authRequestDTO.getPassword(), userByLogin.getPassword())) {
            throw new AuthException(ErrorCodeMessage.ERROR_CODE_AUTH_FAILED);
        }
        return UserDTOMapper.convertToDTO(userByLogin);
    }

    /**
     * Accesses the corresponding DAO method to get User object with specific id.
     *
     * @param id User id.
     * @return object with User data.
     */
    @Override
    public UserDTO finById(Integer id) {
        Optional<User> optionalUser = userDAO.find(id);
        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException(
                ErrorCodeMessage.ERROR_CODE_USER_NOT_FOUND));
        return UserDTOMapper.convertToDTO(user);
    }

    /**
     * Accesses the corresponding DAO method to get all Users.
     *
     * @param pageNumber number of page.
     * @param size number of Users on page.
     * @return List of objects with User data.
     */
    @Override
    public List<UserDTO> findAll(Integer pageNumber, Integer size) {
        List<User> userList = userDAO.findAll(pageNumber, size);
        if (userList.isEmpty()) {
            throw new NotExistingPageException(ErrorCodeMessage.ERROR_CODE_PAGE_NOT_FOUND);
        }
        return UserDTOMapper.convertToDTO(userList);
    }

    /**
     * Calculates the total number of pages required to display all Users.
     *
     * @return the total number of pages required to display all Users.
     */
    public Integer findNumberPagesForAllUsers(Integer size) {
        Integer totalNumberUsers = userDAO.countAll();
        return totalNumberUsers % size == 0 ? totalNumberUsers / size : totalNumberUsers / size + 1;
    }

    /**
     * Accesses the corresponding DAO method to get User with provided login.
     *
     * @param login user login.
     * @return object with user data.
     * @throws UsernameNotFoundException if the user with the specified login is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> optionalUser = userDAO.findByLogin(login);
        User user = optionalUser.orElseThrow(() ->
                new UsernameNotFoundException(ErrorCodeMessage.ERROR_CODE_USER_NOT_FOUND));
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRole().getName())
                .build();
        return userDetails;
    }
}
