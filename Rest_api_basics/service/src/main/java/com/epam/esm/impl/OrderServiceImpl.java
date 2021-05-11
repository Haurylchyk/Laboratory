package com.epam.esm.impl;

import com.epam.esm.OrderService;
import com.epam.esm.constant.ErrorCodeMessage;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.mapper.OrderDTOMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.impl.EntityNotFoundException;
import com.epam.esm.exception.impl.InvalidDataException;
import com.epam.esm.exception.impl.NotExistingPageException;
import com.epam.esm.validator.CommonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class implements interface OrderService. Describes the service
 * for working with OrderDTO.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
@Service
public class OrderServiceImpl implements OrderService {

    /**
     * Object of the OrderDAO type.
     */
    private final OrderDAO orderDAO;

    /**
     * Object of the UserDAO type.
     */
    private final UserDAO userDAO;

    /**
     * Object of the GiftCertificateDAO type.
     */
    private final GiftCertificateDAO giftCertificateDAO;

    /**
     * Constructor with parameter.
     *
     * @param orderDAO interface providing DAO methods.
     */
    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, UserDAO userDAO, GiftCertificateDAO giftCertificateDAO) {
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
        this.giftCertificateDAO = giftCertificateDAO;
    }

    /**
     * Accesses the corresponding DAO method to create a new Order object.
     *
     * @param userId                 id of the User who made the Order.
     * @param giftCertificatesIdList list of GiftCertificate IDs in the Order.
     * @return created object with Order data.
     */
    @Override
    @Transactional
    public OrderDTO create(Integer userId, List<Integer> giftCertificatesIdList) {
        if (CommonValidator.isEmpty(giftCertificatesIdList)) {
            throw new InvalidDataException(ErrorCodeMessage.ERROR_CODE_ORDER_INVALID_DATA);
        }
        Optional<User> optionalUser = userDAO.find(userId);
        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException(
                ErrorCodeMessage.ERROR_CODE_USER_NOT_FOUND));

        List<GiftCertificate> giftCertificateList = new ArrayList<>();
        int cost = 0;

        for (Integer giftCertificateId : giftCertificatesIdList) {
            Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDAO.find(giftCertificateId);

            GiftCertificate giftCertificate = optionalGiftCertificate.orElseThrow(
                    () -> new EntityNotFoundException(ErrorCodeMessage.ERROR_CODE_GC_NOT_FOUND));

            cost += giftCertificate.getPrice();
            giftCertificateList.add(giftCertificate);
        }

        Order order = new Order();
        LocalDateTime currentLocalDateTime = LocalDateTime.now();

        order.setUser(user);
        order.setGiftCertificateList(giftCertificateList);
        order.setCost(cost);
        order.setDate(currentLocalDateTime);

        Order resultOrder = orderDAO.save(order);
        return OrderDTOMapper.convertToDTO(resultOrder);
    }

    /**
     * Accesses the corresponding DAO method to find Order object with specific id.
     *
     * @param id Order id.
     * @return object with Order data.
     */
    @Override
    public OrderDTO findById(Integer id) {
        Optional<Order> optionalOrder = orderDAO.find(id);
        Order order = optionalOrder.orElseThrow(() -> new EntityNotFoundException(
                ErrorCodeMessage.ERROR_CODE_ORDER_NOT_FOUND));
        return OrderDTOMapper.convertToDTO(order);
    }

    /**
     * Accesses the corresponding DAO method to find all Orders.
     *
     * @param pageNumber number of page.
     * @param size number of Orders on page.
     * @return List of objects with Order data.
     */
    @Override
    public List<OrderDTO> findAll(Integer pageNumber, Integer size) {
        List<Order> orderList = orderDAO.findAll(pageNumber, size);
        if (orderList.isEmpty()) {
            throw new NotExistingPageException(ErrorCodeMessage.ERROR_CODE_PAGE_NOT_FOUND);
        }
        return OrderDTOMapper.convertToDTO(orderList);
    }

    /**
     * Accesses the corresponding DAO method to find all Orders
     * for User with a specific id.
     *
     * @param id User id.
     * @return List of objects with Order data.
     */
    @Override
    public List<OrderDTO> findByUserId(Integer id, Integer page, Integer size) {
        List<Order> orderList = orderDAO.findOrdersByUserId(id, page, size);
        return OrderDTOMapper.convertToDTO(orderList);
    }

    /**
     * Calculates the total number of pages required to display all Orders.
     *
     * @return the total number of pages required to display all Orders.
     */
    public Integer findNumberPagesForAllOrders(Integer size) {
        Integer totalNumberOrders = orderDAO.countAll();
        return totalNumberOrders % size == 0 ? totalNumberOrders / size : totalNumberOrders / size + 1;
    }
}
