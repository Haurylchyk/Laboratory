package com.epam.esm;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.impl.EntityNotFoundException;
import com.epam.esm.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    private static final Integer TEST_ID = 1;
    private static final int TEST_USER_ID = 2;
    private static final int NOT_EXIST_USER_ID = 100;
    private static final int NOT_EXIST_GС_ID = 10;

    private static final int TEST_GС_ID = 3;
    private static final int TEST_PRICE = 250;
    private static final int TEST_DURATION = 10;
    private static final LocalDateTime TEST_DATE = LocalDateTime.now();

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderDAO orderDAO;
    @Mock
    private UserDAO userDAO;
    @Mock
    private GiftCertificateDAO giftCertificateDAO;

    private Order testOrder;
    private OrderDTO testOrderDTO;
    private User testUser;
    private UserDTO userDTO;
    private GiftCertificate testGiftCertificate;
    private GiftCertificateDTO testGiftCertificateDTO;
    private List<GiftCertificate> giftCertificateList;
    private List<GiftCertificateDTO> giftCertificateDTOList;
    private List<Order> orderList;
    private List<Order> emptyOrderList;
    private List<OrderDTO> orderDTOList;
    private List<Integer> giftCertificateIdList;
    private List<Integer> notExistGCIdList;


    @BeforeEach
    public void setUp() {
        giftCertificateIdList = new ArrayList<>();
        giftCertificateIdList.add(TEST_GС_ID);

        notExistGCIdList = new ArrayList<>();
        notExistGCIdList.add(NOT_EXIST_GС_ID);

        testUser = new User();
        testUser.setId(TEST_USER_ID);

        testGiftCertificate = new GiftCertificate();
        testGiftCertificate.setId(TEST_GС_ID);
        testGiftCertificate.setPrice(TEST_PRICE);
        testGiftCertificate.setDuration(TEST_DURATION);
        testGiftCertificate.setCreateDate(TEST_DATE);
        testGiftCertificate.setLastUpdateDate(TEST_DATE);


        testGiftCertificateDTO = new GiftCertificateDTO();
        testGiftCertificateDTO.setId(TEST_GС_ID);
        testGiftCertificateDTO.setPrice(TEST_PRICE);
        testGiftCertificateDTO.setDuration(TEST_DURATION);
        testGiftCertificateDTO.setCreateDate(TEST_DATE);
        testGiftCertificateDTO.setLastUpdateDate(TEST_DATE);

        giftCertificateDTOList = new ArrayList<>();
        giftCertificateDTOList.add(testGiftCertificateDTO);

        userDTO = new UserDTO();
        userDTO.setId(TEST_USER_ID);

        giftCertificateList = new ArrayList<>();
        giftCertificateList.add(testGiftCertificate);

        testOrder = new Order();
        testOrder.setUser(testUser);
        testOrder.setGiftCertificateList(giftCertificateList);
        testOrder.setId(TEST_ID);
        testOrder.setCost(TEST_PRICE);
        testOrder.setDate(TEST_DATE);

        orderList = new ArrayList<>();
        orderList.add(testOrder);

        emptyOrderList = new ArrayList<>();

        testOrderDTO = new OrderDTO();
        testOrderDTO.setGiftCertificateList(giftCertificateDTOList);
        testOrderDTO.setId(TEST_ID);
        testOrderDTO.setCost(TEST_PRICE);
        testOrderDTO.setDate(TEST_DATE);

        orderDTOList = new ArrayList<>();
        orderDTOList.add(testOrderDTO);

    }

    @Test
    public void createShouldReturnCreatedOrder() {
        given(orderDAO.save(any())).willReturn(testOrder);
        given(userDAO.find(any())).willReturn(Optional.of(testUser));
        given(giftCertificateDAO.find(any())).willReturn(Optional.of(testGiftCertificate));

        OrderDTO receivedDTO = orderService.create(TEST_USER_ID, giftCertificateIdList);

        assertEquals(TEST_ID, receivedDTO.getId());
        assertEquals(giftCertificateDTOList, receivedDTO.getGiftCertificateList());
        assertEquals(TEST_DATE, receivedDTO.getDate());
        assertEquals(TEST_PRICE, receivedDTO.getCost());
    }

    @Test
    public void createShouldReturnNotFoundException() {
            given(userDAO.find(any())).willReturn(Optional.empty());
            assertThrows(EntityNotFoundException.class,
                    () -> orderService.create(NOT_EXIST_USER_ID, giftCertificateIdList));
    }

    @Test
    public void createShouldNotFoundException() {
        given(userDAO.find(any())).willReturn(Optional.of(testUser));
        given(giftCertificateDAO.find(any())).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
                () -> orderService.create(TEST_USER_ID, notExistGCIdList));
    }

    @Test
    public void findByIdShouldSuccessfully() {
            given(orderDAO.find(anyInt())).willReturn(Optional.of(testOrder));
            OrderDTO receivedOrderDto = orderService.findById(TEST_ID);
            assertEquals(testOrderDTO, receivedOrderDto);
    }

    @Test
    public void findByIdShouldNotFoundException() {
        given(orderDAO.find(anyInt())).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> orderService.findById(TEST_ID));
    }

    @Test
    public void findByByUserIdShouldSuccessfully() {
            given(orderDAO.findOrdersByUserId(TEST_USER_ID, 1, 2)).willReturn(orderList);
            List<OrderDTO> receivedDtoList = orderService.findByUserId(TEST_USER_ID, 1, 2);
            assertIterableEquals(orderDTOList, receivedDtoList);
    }

    @Test
    public void findByByUserIdShouldNotFoundException() {
        given(orderDAO.findOrdersByUserId(NOT_EXIST_USER_ID, 1, 2)).willReturn(emptyOrderList);
        List<OrderDTO> receivedDtoList = orderService.findByUserId(NOT_EXIST_USER_ID, 1, 2);
        assertEquals(emptyOrderList, receivedDtoList);
    }
}
