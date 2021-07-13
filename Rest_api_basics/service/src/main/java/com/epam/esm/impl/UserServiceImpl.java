package com.epam.esm.impl;

import com.epam.esm.UserService;
import com.epam.esm.constant.ErrorCodeMessage;
import com.epam.esm.dao.UserRepository;
import com.epam.esm.entity.User;
import com.epam.esm.entity.field.Role;
import com.epam.esm.exception.impl.AuthException;
import com.epam.esm.exception.impl.EntityNotFoundException;
import com.epam.esm.exception.impl.ExistingUserException;
import com.epam.esm.exception.impl.NotExistingPageException;
import com.epam.esm.model.dto.AuthRequestDTO;
import com.epam.esm.model.dto.SignUpUserDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.dto.mapper.impl.UserDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
     * The index of the first item in the list.
     */
    private static final int FIRST_ELEMENT_INDEX = 0;

    /**
     * Object of the UserDAO type.
     */
    private final UserRepository userRepository;

    /**
     * Object intended for encoding a password.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Object intended for converting User to UserDTO and vice versa.
     */
    private final UserDTOMapper userDTOMapper;

    /**
     * Constructor with parameter.
     *
     * @param userRepository interface providing DAO methods.
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDTOMapper userDTOMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDTOMapper = userDTOMapper;
    }

    /**
     * Accesses the corresponding DAO method to create a new User object.
     *
     * @param signUpUserDTO object containing the data required to create a User.
     * @return created object with User data.
     */
    @Override
    public UserDTO signUp(SignUpUserDTO signUpUserDTO) {
        List<User> userList = userRepository.findByLogin(signUpUserDTO.getLogin());
        if (!userList.isEmpty()) {
            throw new ExistingUserException(ErrorCodeMessage.ERROR_CODE_USER_EXISTS);
        }
        User user = createUser(signUpUserDTO);
        User newUser = userRepository.save(user);
        return userDTOMapper.convertToDTO(newUser);
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
        List<User> userList = userRepository.findByLogin(login);
        if (userList.isEmpty()) {
            throw new UsernameNotFoundException(ErrorCodeMessage.ERROR_CODE_USER_NOT_FOUND);
        }
        User userByLogin = userList.get(FIRST_ELEMENT_INDEX);
        if (!passwordEncoder.matches(authRequestDTO.getPassword(), userByLogin.getPassword())) {
            throw new AuthException(ErrorCodeMessage.ERROR_CODE_AUTH_FAILED);
        }
        return userDTOMapper.convertToDTO(userByLogin);
    }

    /**
     * Accesses the corresponding DAO method to get User object with specific id.
     *
     * @param id User id.
     * @return object with User data.
     */
    @Override
    public UserDTO finById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException(
                ErrorCodeMessage.ERROR_CODE_USER_NOT_FOUND));
        return userDTOMapper.convertToDTO(user);
    }

    /**
     * Accesses the corresponding DAO method to get all Users.
     *
     * @param pageable object contains page number and page size.
     * @return List of objects with User data.
     */
    @Override
    public Page<UserDTO> findAll(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        List<User> userList = userPage.toList();
        if (userList.isEmpty() && pageable.getPageNumber() == 0) {
            throw new EntityNotFoundException(ErrorCodeMessage.ERROR_CODE_USER_NOT_FOUND);
        }
        if (userList.isEmpty()) {
            throw new NotExistingPageException(ErrorCodeMessage.ERROR_CODE_PAGE_NOT_FOUND);
        }
        return new PageImpl<>(userDTOMapper.convertToDTO(userList), pageable, userPage.getTotalElements());
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
        List<User> userList = userRepository.findByLogin(login);
        if (userList.isEmpty()) {
            throw new UsernameNotFoundException(ErrorCodeMessage.ERROR_CODE_USER_NOT_FOUND);
        }
        User user = userRepository.findByLogin(login).get(FIRST_ELEMENT_INDEX);
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
        return userDetails;
    }

    private User createUser(SignUpUserDTO signUpUserDTO) {
        User user = new User();
        user.setName(signUpUserDTO.getName());
        user.setLogin(signUpUserDTO.getLogin());
        user.setPassword(passwordEncoder.encode(signUpUserDTO.getPassword()));
        user.setRole(Role.USER);
        return user;
    }
}
