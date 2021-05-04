package com.epam.esm.impl;

import com.epam.esm.UserService;
import com.epam.esm.constant.ErrorCodeMessage;
import com.epam.esm.constant.PaginationConstant;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.dto.mapper.UserDTOMapper;
import com.epam.esm.entity.User;
import com.epam.esm.exception.impl.*;
import com.epam.esm.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserServiceImpl implements UserService {

    /**
     * Object of the UserDAO type.
     */
    private final UserDAO userDAO;

    /**
     * Constructor with parameter.
     *
     * @param userDAO interface providing DAO methods.
     */
    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Accesses the corresponding DAO method to create a new User object.
     *
     * @param userDTO object with User data.
     * @return created object with User data.
     */
    @Override
    public UserDTO create(UserDTO userDTO) {
        if (!UserValidator.isValidData(userDTO)) {
            throw new InvalidDataException(ErrorCodeMessage.ERROR_CODE_USER_INVALID_DATA);
        }
        User user = UserDTOMapper.convertToEntity(userDTO);
        Optional<User> userByLogin = userDAO.findByLogin(user.getLogin());
        if (userByLogin.isPresent()) {
            throw new ExistingUserException(ErrorCodeMessage.ERROR_CODE_USER_EXISTS);
        }
        User newUser = userDAO.save(user);
        return UserDTOMapper.convertToDTO(newUser);
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
        if (pageNumber == null) {
            pageNumber = PaginationConstant.DEFAULT_PAGE;
        }
        if (size == null) {
            size = PaginationConstant.DEFAULT_NUMBER_ON_PAGE;
        }
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
}
