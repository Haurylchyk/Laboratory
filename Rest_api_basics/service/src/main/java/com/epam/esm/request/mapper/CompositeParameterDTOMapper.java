package com.epam.esm.request.mapper;

import com.epam.esm.dao.query.GiftCertificateCompositeParameter;
import com.epam.esm.request.GiftCertificateCompositeParameterDTO;

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
        parameter.setSortType(parameterDTO.getSortType().toString());
        parameter.setSortOrder(parameterDTO.getSortOrder().toString());

        return parameter;
    }
}
