package com.epam.esm.dto.mapper;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.ArrayList;
import java.util.List;

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
}
