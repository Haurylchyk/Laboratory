package com.epam.esm.model.dto.mapper.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.mapper.AbstractMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The class converts GiftCertificate to GiftCertificateDTO and vice versa.
 */
@Component
public class GiftCertificateDTOMapper extends AbstractMapper<GiftCertificate, GiftCertificateDTO> {

    public GiftCertificateDTOMapper(ModelMapper modelMapper) {
        super(GiftCertificate.class, GiftCertificateDTO.class, modelMapper);
    }

    public GiftCertificateDTO convertToDTO(GiftCertificate giftCertificate) {
        if (Objects.isNull(giftCertificate))
            return null;
        else {
            GiftCertificateDTO giftCertificateDTO = modelMapper.map(giftCertificate, GiftCertificateDTO.class);
            giftCertificateDTO.setTagNames(giftCertificate.getTagList().stream().map(tag -> tag.getName())
                    .collect(Collectors.toList()));
            return giftCertificateDTO;
        }
    }

    public void map(Object source, Object destination) {
        modelMapper.map(source, destination);
    }
}

