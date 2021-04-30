package com.epam.esm.dto.mapper;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        return orderList.stream().map(order -> convertToDTO(order)).collect(Collectors.toList());
    }
}
