package com.epam.esm;

import com.epam.esm.config.RepositoryConfigTest;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.impl.OrderDAOImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = OrderDAOImpl.class)
@ContextConfiguration(classes = RepositoryConfigTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderDAOImplTest {

    @Autowired
    private OrderDAO orderDAO;

    private static final int TEST_ID = 1;
    private static final int INVALID_ID = 100;
    private static final int COST = 100;

    @Test
    public void createShouldReturnCreatedOrder() {
        final int GС_NUMBER = 1;
        User user = new User();
        user.setId(TEST_ID);
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(TEST_ID);
        List<GiftCertificate> giftCertificateList = new ArrayList<>();
        giftCertificateList.add(giftCertificate);
        Order order = new Order();
        order.setUser(user);
        order.setGiftCertificateList(giftCertificateList);
        order.setCost(COST);
        order.setDate(LocalDateTime.now());
        ;

        Order newOrder = orderDAO.save(order);

        assertNotNull(newOrder);
        assertEquals(TEST_ID, newOrder.getUser().getId());
        assertEquals(GС_NUMBER, newOrder.getGiftCertificateList().size());
        assertEquals(COST, newOrder.getCost());
    }

    @Test
    public void findShouldSuccessfully() {
        Optional<Order> existOrder = orderDAO.find(TEST_ID);
        assertTrue(existOrder.isPresent());
    }

    @Test
    public void findShouldReturnNull() {
        Optional<Order> notExistOrder = orderDAO.find(INVALID_ID);
        assertFalse(notExistOrder.isPresent());
    }

    @Test
    public void findOrdersByUserIdShouldSuccessfully() {
        final int ORDER_NUMBER = 2;
        List<Order> orderList = orderDAO.findOrdersByUserId(TEST_ID);
        assertEquals(ORDER_NUMBER, orderList.size());
    }

    @Test
    public void findOrdersByUserIdShouldReturnEmpty() {
        List<Order> orderList = orderDAO.findOrdersByUserId(INVALID_ID);
        assertTrue(orderList.isEmpty());
    }
}
