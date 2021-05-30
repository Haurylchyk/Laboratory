package com.epam.esm.model.dto.mapper;

import com.epam.esm.entity.OrderGiftCertificate;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.entity.Order;
import com.epam.esm.model.dto.OrderGiftCertificateDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The class converts Order to OrderDTO and vice versa.
 */
public class OrderDTOMapper {

    private OrderDTOMapper() {
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

        List<OrderGiftCertificateDTO> orderGiftCertificateDTOList = OrderGiftCertificateDTOMapper
                .convertToDTO(order.getGiftCertificateList());
        orderDTO.setGiftCertificateList(orderGiftCertificateDTOList);

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
