package com.epam.esm.model.dto.mapper;

import com.epam.esm.entity.OrderGiftCertificate;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.OrderGiftCertificateDTO;

import java.util.List;
import java.util.stream.Collectors;

public class OrderGiftCertificateDTOMapper {

    /**
     * Converts OrderGiftCertificate to OrderGiftCertificateDTO.
     *
     * @param orderGiftCertificate object of OrderGiftCertificate type.
     * @return object of OrderGiftCertificateDTO type.
     */
    public static OrderGiftCertificateDTO convertToDTO(OrderGiftCertificate orderGiftCertificate) {
        OrderGiftCertificateDTO orderGiftCertificateDTO = new OrderGiftCertificateDTO();

//        orderGiftCertificateDTO.setId(orderGiftCertificate.getId());

        GiftCertificateDTO giftCertificateDTO = GiftCertificateDTOMapper
                .convertToDTO(orderGiftCertificate.getGiftCertificate());
        orderGiftCertificateDTO.setGiftCertificate(giftCertificateDTO);

        return orderGiftCertificateDTO;
    }

    /**
     * Converts list of OrderGiftCertificate to list of OrderGiftCertificateDTO.
     *
     * @param orderGiftCertificateList list of objects of OrderGiftCertificate type.
     * @return list of objects of OrderGiftCertificateDTO type.
     */
    public static List<OrderGiftCertificateDTO> convertToDTO(List<OrderGiftCertificate> orderGiftCertificateList) {
        return orderGiftCertificateList.stream().map(orderGiftCertificate -> convertToDTO(orderGiftCertificate)).collect(Collectors.toList());
    }
}
