package com.epam.esm.dto.mapper;

import com.epam.esm.dao.query.GiftCertificateParam;
import com.epam.esm.dao.query.sort.SortOrder;
import com.epam.esm.dao.query.sort.SortType;
import com.epam.esm.dto.GiftCertificateParamDTO;

public class GiftCertificateParamDTOMapper {

    private GiftCertificateParamDTOMapper() {
    }

    /**
     * Converts GiftCertificateCompositeParameterDTO to
     * GiftCertificateCompositeParameter.
     *
     * @param parameterDTO object of GiftCertificateCompositeParameterDTO type.
     * @return object of GiftCertificateCompositeParameter type.
     */
    public static GiftCertificateParam convertToEntity(GiftCertificateParamDTO parameterDTO) {
        GiftCertificateParam parameter = new GiftCertificateParam();
        parameter.setTagName(parameterDTO.getTagName());
        parameter.setName(parameterDTO.getName());
        parameter.setDescription(parameterDTO.getDescription());
        if (parameterDTO.getSortType() != null) {
            parameter.setSortType(SortType.valueOf(parameterDTO.getSortType().toUpperCase()));
        }
        if (parameterDTO.getSortOrder() != null) {
            parameter.setSortOrder(SortOrder.valueOf(parameterDTO.getSortOrder().toUpperCase()));
        }
        return parameter;
    }
}