package com.epam.esm.dto.mapper;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.UserInOrderDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * The class converts Order to OrderDTO and vice versa.
 */
public class OrderDTOMapper {

    private OrderDTOMapper() {
    }

    /**
     * Converts OrderDTO to Order.
     *
     * @param orderDTO object of OrderDTO type.
     * @return object of Order type.
     */
    public static Order convertToEntity(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setCost(orderDTO.getCost());
        order.setDate(orderDTO.getDate());
        List<GiftCertificate> giftCertificateList = GiftCertificateDTOMapper
                .convertToEntity(orderDTO.getGiftCertificateList());
        order.setGiftCertificateList(giftCertificateList);

        return order;
    }

    /**
     * Converts Order to OrderDTO.
     *
     * @param order object of Order type.
     * @return object of OrderDTO type.
     */
    public static OrderDTO convertToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setId(order.getId());

        UserInOrderDTO userInOrder = toUserInOrderDTO(order.getUser());
        orderDTO.setUser(userInOrder);

        List<GiftCertificateDTO> giftCertificateDTOList = GiftCertificateDTOMapper
                .convertToDTO(order.getGiftCertificateList());
        orderDTO.setGiftCertificateList(giftCertificateDTOList);

        orderDTO.setCost(order.getCost());
        orderDTO.setDate(order.getDate());

        return orderDTO;
    }

    /**
     * Converts list of Order to list of OrderDTO.
     *
     * @param orderList list of objects of Order type.
     * @return list of objects of OrderDTO type.
     */
    public static List<OrderDTO> convertToDTO(List<Order> orderList) {
        List<OrderDTO> orderDTOList = new ArrayList<>();
        orderList.forEach(tag -> orderDTOList.add(convertToDTO(tag)));

        return orderDTOList;
    }

    private static UserInOrderDTO toUserInOrderDTO(User user) {
        UserInOrderDTO userInOrder = new UserInOrderDTO();

        userInOrder.setId(user.getId());
        userInOrder.setName(user.getName());
        userInOrder.setLogin(user.getLogin());

        return userInOrder;
    }


}
