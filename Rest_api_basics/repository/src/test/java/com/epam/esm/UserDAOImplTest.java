package com.epam.esm;

import com.epam.esm.config.RepositoryConfigTest;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dao.impl.UserDAOImpl;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = UserDAOImpl.class)
@ContextConfiguration(classes = RepositoryConfigTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserDAOImplTest {

    @Autowired
    private UserDAO userDAO;

    private static final int USER_ID = 1;
    private static final int INVALID_ID = 100;

    private static final String USER_LOGIN = "dartagnan";
    private static final String INVALID_LOGIN = "snake";

    @Test
    public void findShouldSuccessfully() {
        Optional<User> existUser = userDAO.find(USER_ID);
        assertTrue(existUser.isPresent());
    }

    @Test
    public void findShouldReturnNull() {
        Optional<User> notExistUser = userDAO.find(INVALID_ID);
        assertFalse(notExistUser.isPresent());
    }

    @Test
    public void findByLoginShouldSuccessfully() {
        Optional<User> existUser = userDAO.findByLogin(USER_LOGIN);
        assertTrue(existUser.isPresent());
    }

    @Test
    public void findByLoginShouldReturnNull() {
        Optional<User> notExistUser = userDAO.findByLogin(INVALID_LOGIN);
        assertFalse(notExistUser.isPresent());
    }

    @Test
    public void findUserWithTopOrdersShouldSuccessfully() {
        User topUser = userDAO.findUserWithTopOrders();
        assertEquals(USER_LOGIN, topUser.getLogin());
    }
}
