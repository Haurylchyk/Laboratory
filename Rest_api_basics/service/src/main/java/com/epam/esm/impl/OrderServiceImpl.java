package com.epam.esm.impl;

import com.epam.esm.OrderService;
import com.epam.esm.constant.ErrorCodeMessage;
import com.epam.esm.dao.GiftCertificateRepository;
import com.epam.esm.dao.OrderRepository;
import com.epam.esm.dao.UserRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderGiftCertificate;
import com.epam.esm.entity.User;
import com.epam.esm.exception.impl.EntityNotFoundException;
import com.epam.esm.exception.impl.InvalidDataException;
import com.epam.esm.exception.impl.NotExistingPageException;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.mapper.impl.OrderDTOMapper;
import com.epam.esm.validator.CommonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    private final OrderRepository orderRepository;

    /**
     * Object of the UserDAO type.
     */
    private final UserRepository userRepository;

    /**
     * Object of the GiftCertificateDAO type.
     */
    private final GiftCertificateRepository giftCertificateRepository;

    /**
     * Object intended for converting Order to OrderDTO and vice versa.
     */
    private final OrderDTOMapper orderDTOMapper;

    /**
     * Constructor with parameter.
     *
     * @param orderRepository interface providing DAO methods.
     */
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository,
                            GiftCertificateRepository giftCertificateRepository, OrderDTOMapper orderDTOMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.giftCertificateRepository = giftCertificateRepository;
        this.orderDTOMapper = orderDTOMapper;
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
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException(
                ErrorCodeMessage.ERROR_CODE_USER_NOT_FOUND));

        List<OrderGiftCertificate> orderGiftCertificateList = new ArrayList<>();
        int cost = 0;

        for (Integer giftCertificateId : giftCertificatesIdList) {
            Optional<GiftCertificate> optionalGiftCertificate = giftCertificateRepository.findById(giftCertificateId);

            GiftCertificate giftCertificate = optionalGiftCertificate.orElseThrow(
                    () -> new EntityNotFoundException(ErrorCodeMessage.ERROR_CODE_GC_NOT_FOUND));

            cost += giftCertificate.getPrice();
            orderGiftCertificateList.add(new OrderGiftCertificate(giftCertificate));
        }

        Order order = new Order();
        LocalDateTime currentLocalDateTime = LocalDateTime.now();

        order.setUser(user);
        order.setGiftCertificateList(orderGiftCertificateList);
        order.setCost(cost);
        order.setDate(currentLocalDateTime);

        Order resultOrder = orderRepository.save(order);
        return orderDTOMapper.convertToDTO(resultOrder);
    }

    /**
     * Accesses the corresponding DAO method to find Order object with specific id.
     *
     * @param id Order id.
     * @return object with Order data.
     */
    @Override
    public OrderDTO findById(Integer id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        Order order = optionalOrder.orElseThrow(() -> new EntityNotFoundException(
                ErrorCodeMessage.ERROR_CODE_ORDER_NOT_FOUND));
        return orderDTOMapper.convertToDTO(order);
    }

    /**
     * Accesses the corresponding DAO method to find all Orders.
     *
     * @param pageable object contains page number and page size.
     * @return List of objects with Order data.
     */
    @Override
    public Page<OrderDTO> findAll(Pageable pageable) {
        Page<Order> orderPage = orderRepository.findAll(pageable);
        List<Order> orderList = orderPage.toList();
        if (orderList.isEmpty()) {
            throw new NotExistingPageException(ErrorCodeMessage.ERROR_CODE_PAGE_NOT_FOUND);
        }
        return new PageImpl<>(orderDTOMapper.convertToDTO(orderList), pageable, orderPage.getTotalElements());
    }

    /**
     * Accesses the corresponding DAO method to find all Orders
     * for User with a specific id.
     *
     * @param id       User id.
     * @param pageable object contains page number and page size.
     * @return List of objects with Order data.
     */
    @Override
    public Page<OrderDTO> findByUserId(Integer id, Pageable pageable) {
        Page<Order> orderPage = orderRepository.findByUserId(id, pageable);
        List<Order> orderList = orderPage.toList();
        return new PageImpl<>(orderDTOMapper.convertToDTO(orderList), pageable, orderPage.getTotalElements());
    }
}
