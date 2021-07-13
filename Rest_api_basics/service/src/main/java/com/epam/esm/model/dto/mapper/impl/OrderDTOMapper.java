package com.epam.esm.model.dto.mapper.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderGiftCertificate;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.OrderGiftCertificateDTO;
import com.epam.esm.model.dto.mapper.AbstractMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The class converts Order to OrderDTO and vice versa.
 */
@Component
public class OrderDTOMapper extends AbstractMapper<Order, OrderDTO> {

    private final GiftCertificateDTOMapper giftCertificateDTOMapper;

    @Autowired
    public OrderDTOMapper(ModelMapper modelMapper, GiftCertificateDTOMapper giftCertificateDTOMapper) {
        super(Order.class, OrderDTO.class, modelMapper);
        this.giftCertificateDTOMapper = giftCertificateDTOMapper;
    }

    public OrderDTO convertToDTO(Order order) {
        if (Objects.isNull(order))
            return null;
        else {
            OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
            orderDTO.setGiftCertificateList(convertToList(order.getGiftCertificateList()));
            return orderDTO;
        }
    }

    private OrderGiftCertificateDTO convertToDTO(OrderGiftCertificate orderGiftCertificate) {
        OrderGiftCertificateDTO orderGiftCertificateDTO = new OrderGiftCertificateDTO();

        GiftCertificateDTO giftCertificateDTO = giftCertificateDTOMapper.convertToDTO(orderGiftCertificate.getGiftCertificate());
        orderGiftCertificateDTO.setGiftCertificate(giftCertificateDTO);

        return orderGiftCertificateDTO;
    }

    private List<OrderGiftCertificateDTO> convertToList(List<OrderGiftCertificate> orderGiftCertificateList) {
        return orderGiftCertificateList.stream().map(orderGiftCertificate -> convertToDTO(orderGiftCertificate)).collect(Collectors.toList());
    }
}

