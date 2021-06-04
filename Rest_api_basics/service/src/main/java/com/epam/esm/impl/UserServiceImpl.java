package com.epam.esm.impl;

import com.epam.esm.UserService;
import com.epam.esm.constant.ErrorCodeMessage;
import com.epam.esm.constant.RoleNameConstant;
import com.epam.esm.dao.RoleRepository;
import com.epam.esm.dao.UserRepository;
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
     * Object of the RoleDAO type.
     */
    private final RoleRepository roleRepository;

    /**
     * Object intended for encoding a password.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor with parameter.
     *
     * @param userRepository interface providing DAO methods.
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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
        List<User> userList = userRepository.findByLogin(creatingUserData.getLogin());
        if (!userList.isEmpty()) {
            throw new ExistingUserException(ErrorCodeMessage.ERROR_CODE_USER_EXISTS);
        }
        User user = new User();
        user.setName(creatingUserData.getName());
        user.setLogin(creatingUserData.getLogin());
        user.setPassword(passwordEncoder.encode(creatingUserData.getPassword()));
        Role role = roleRepository.findByName(RoleNameConstant.USER);
        user.setRole(role);
        User newUser = userRepository.save(user);
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
        List<User> userList = userRepository.findByLogin(login);
        if (userList.isEmpty()) {
            throw new UsernameNotFoundException(ErrorCodeMessage.ERROR_CODE_USER_NOT_FOUND);
        }
        User userByLogin = userList.get(FIRST_ELEMENT_INDEX);
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
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException(
                ErrorCodeMessage.ERROR_CODE_USER_NOT_FOUND));
        return UserDTOMapper.convertToDTO(user);
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
        if (userList.isEmpty() && pageable.getPageNumber() == 1) {
            throw new EntityNotFoundException(ErrorCodeMessage.ERROR_CODE_USER_NOT_FOUND);
        }
        if (userList.isEmpty()) {
            throw new NotExistingPageException(ErrorCodeMessage.ERROR_CODE_PAGE_NOT_FOUND);
        }
        return new PageImpl<>(UserDTOMapper.convertToDTO(userList), pageable, userPage.getTotalElements());
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
                .roles(user.getRole().getName())
                .build();
        return userDetails;
    }
}
