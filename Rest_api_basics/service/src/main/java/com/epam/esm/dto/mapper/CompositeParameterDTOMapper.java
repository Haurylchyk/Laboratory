package com.epam.esm.dto.mapper;

import com.epam.esm.dao.query.GiftCertificateCompositeParameter;
import com.epam.esm.dto.GiftCertificateCompositeParameterDTO;

public class CompositeParameterDTOMapper {

    private CompositeParameterDTOMapper() {
    }

    /**
     * Converts GiftCertificateCompositeParameterDTO to
     * GiftCertificateCompositeParameter.
     *
     * @param parameterDTO object of GiftCertificateCompositeParameterDTO type.
     * @return object of GiftCertificateCompositeParameter type.
     */
    public static GiftCertificateCompositeParameter convertToEntity(GiftCertificateCompositeParameterDTO parameterDTO) {
        GiftCertificateCompositeParameter parameter = new GiftCertificateCompositeParameter();
        parameter.setTagName(parameterDTO.getTagName());
        parameter.setName(parameterDTO.getName());
        parameter.setDescription(parameterDTO.getDescription());
        parameter.setSortType(parameterDTO.getSortType());
        parameter.setSortOrder(parameterDTO.getSortOrder());

        return parameter;
    }
}
