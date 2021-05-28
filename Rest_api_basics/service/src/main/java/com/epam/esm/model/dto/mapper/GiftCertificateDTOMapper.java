package com.epam.esm.model.dto.mapper;

import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class converts GiftCertificate to GiftCertificateDTO and vice versa.
 */
public class GiftCertificateDTOMapper {

    private GiftCertificateDTOMapper() {}

    /**
     * Converts GiftCertificateDTO to GiftCertificate.
     *
     * @param giftCertificateDTO object of GiftCertificateDTO type.
     * @return object of GiftCertificate type.
     */
    public static GiftCertificate convertToEntity(GiftCertificateDTO giftCertificateDTO) {
        GiftCertificate giftCertificate = new GiftCertificate();

        if (giftCertificateDTO.getId() != null) {
            giftCertificate.setId(giftCertificateDTO.getId());
        }
        giftCertificate.setName(giftCertificateDTO.getName());
        giftCertificate.setDescription(giftCertificateDTO.getDescription());
        giftCertificate.setPrice(giftCertificateDTO.getPrice());
        giftCertificate.setDuration(giftCertificateDTO.getDuration());
        giftCertificate.setCreateDate(giftCertificateDTO.getCreateDate());
        giftCertificate.setLastUpdateDate(giftCertificateDTO.getLastUpdateDate());

        return giftCertificate;
    }

    /**
     * Converts GiftCertificate to GiftCertificateDTO.
     *
     * @param giftCertificate object of GiftCertificate type.
     * @return object of GiftCertificateDTO type.
     */
    public static GiftCertificateDTO convertToDTO(GiftCertificate giftCertificate) {
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO();

        if (giftCertificate.getId() != null) {
            giftCertificateDTO.setId(giftCertificate.getId());
        }
        giftCertificateDTO.setName(giftCertificate.getName());
        giftCertificateDTO.setDescription(giftCertificate.getDescription());
        giftCertificateDTO.setPrice(giftCertificate.getPrice());
        giftCertificateDTO.setDuration(giftCertificate.getDuration());
        giftCertificateDTO.setCreateDate(giftCertificate.getCreateDate());
        giftCertificateDTO.setLastUpdateDate(giftCertificate.getLastUpdateDate());

        List<Tag> tagList = giftCertificate.getTagList();
        if (tagList != null) {
            List<String> tagNamesList = new ArrayList<>();

            tagList.forEach(tag -> tagNamesList.add(tag.getName()));
            giftCertificateDTO.setTagNames(tagNamesList);
        }

        return giftCertificateDTO;
    }

    /**
     * Converts list of GiftCertificateDTO to list of GiftCertificate.
     *
     * @param giftCertificateDTOList list of objects of GiftCertificateDTO type.
     * @return list of objects of GiftCertificate type.
     */
    public static List<GiftCertificate> convertToEntity(List<GiftCertificateDTO> giftCertificateDTOList) {
        return giftCertificateDTOList.stream().map(giftCertificateDTO -> convertToEntity(giftCertificateDTO))
                .collect(Collectors.toList());
    }

    /**
     * Converts list of GiftCertificate to list of GiftCertificateDTO.
     *
     * @param giftCertificateList list of objects of GiftCertificate type.
     * @return list of objects of GiftCertificateDTO type.
     */
    public static List<GiftCertificateDTO> convertToDTO(List<GiftCertificate> giftCertificateList) {
        return giftCertificateList.stream().map(giftCertificate -> convertToDTO(giftCertificate)).collect(Collectors.toList());
    }
}
