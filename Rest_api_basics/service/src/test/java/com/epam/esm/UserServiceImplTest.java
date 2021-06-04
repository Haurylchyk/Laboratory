package com.epam.esm;

import com.epam.esm.dao.UserRepository;
import com.epam.esm.entity.User;
import com.epam.esm.exception.impl.EntityNotFoundException;
import com.epam.esm.exception.impl.NotExistingPageException;
import com.epam.esm.impl.UserServiceImpl;
import com.epam.esm.model.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private static final int TEST_ID = 1;
    private static final String TEST_USER_NAME = "Tramp";
    private static final String TEST_USER_LOGIN = "trololo";
    private static final Integer PAGE_NUMBER = 1;
    private static final Integer SIZE = 1;
    private static final Integer PAGE_NUMBER_INVALID = 100;

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    private User user;
    private UserDTO userDTO;
    private List<User> userList;
    private List<User> emptyUserList;
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

        emptyUserList = new ArrayList<>();
    }

    @Test
    public void findByIdShouldSuccessfully() {
        given(userRepository.findById(TEST_ID)).willReturn(Optional.of(user));
        UserDTO foundUserDTO = userService.finById(TEST_ID);
        assertEquals(userDTO, foundUserDTO);
    }

    @Test
    public void findByIdShouldNotFoundException() {
        given(userRepository.findById(TEST_ID)).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.finById(TEST_ID));
    }

    @Test
    public void findAllShouldSuccessfully() {
        Page<User> page = new PageImpl<>(userList, Pageable.unpaged(), 2);
        given(userRepository.findAll(Pageable.unpaged())).willReturn(page);
        Page<UserDTO> pageUserDTO = userService.findAll(Pageable.unpaged());
        List<UserDTO> foundUserDTOList = pageUserDTO.toList();
        assertIterableEquals(userListDTO, foundUserDTOList);
    }

    @Test
    public void findAllShouldNotExistingPageException() {
        Page<User> page = new PageImpl<>(emptyUserList, PageRequest.of(PAGE_NUMBER_INVALID, SIZE), 0);
        given(userRepository.findAll(PageRequest.of(PAGE_NUMBER_INVALID, SIZE))).willReturn(page);
        assertThrows(NotExistingPageException.class, () -> userService.findAll(PageRequest.of(PAGE_NUMBER_INVALID, SIZE)));
    }
}