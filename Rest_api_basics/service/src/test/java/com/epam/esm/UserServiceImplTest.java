package com.epam.esm;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
import com.epam.esm.exception.impl.UserNotFoundException;
import com.epam.esm.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private static final int TEST_ID = 1;
    private static final String TEST_USER_NAME = "Tramp";
    private static final String TEST_USER_LOGIN = "trololo";

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserDAO userDAO;

    private User user;
    private UserDTO userDTO;
    private List<User> userList;
    private List<UserDTO> userListDTO;


    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(TEST_ID);
        user.setName(TEST_USER_NAME);
        user.setLogin(TEST_USER_LOGIN);

        userList = new ArrayList<>();
        userList.add(user);

        userDTO = new UserDTO();
        userDTO.setId(TEST_ID);
        userDTO.setName(TEST_USER_NAME);
        userDTO.setLogin(TEST_USER_LOGIN);

        userListDTO = new ArrayList<>();
        userListDTO.add(userDTO);
    }

    @Test
    public void findByIdShouldSuccessfully() {
        given(userDAO.find(TEST_ID)).willReturn(Optional.of(user));
        UserDTO foundUserDTO = userService.finById(TEST_ID);
        assertEquals(userDTO, foundUserDTO);
    }

    @Test
    public void findByIdShouldNotFoundException() {
        given(userDAO.find(TEST_ID)).willReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.finById(TEST_ID));
    }

    @Test
    public void findAllShouldSuccessfully() {
        given(userDAO.findAll()).willReturn(userList);
        List<UserDTO> foundUserDTOList = userService.findAll();
        assertIterableEquals(userListDTO, foundUserDTOList);
    }
}
