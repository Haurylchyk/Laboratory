package com.epam.esm;

import com.epam.esm.dao.query.GiftCertificateCompositeParameter;
import com.epam.esm.dto.GiftCertificateDTO;

import java.util.List;

/**
 * Interface describes the service for working with GiftCertificateDTO.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public interface GiftCertificateService {

    /**
     * Accesses the corresponding DAO method to create a new GiftCertificate object.
     *
     * @param giftCertificateDTO object with GiftCertificate data.
     * @return created object with GiftCertificate data.
     */
    GiftCertificateDTO createGiftCertificate(GiftCertificateDTO giftCertificateDTO);

    /**
     * Accesses the corresponding DAO method to get GiftCertificate object with specific id.
     *
     * @param id GiftCertificate id.
     * @return object with GiftCertificate data.
     */
    GiftCertificateDTO getGiftCertificateById(Integer id);

    /**
     * Accesses the corresponding DAO method to update GiftCertificate with specific id.
     *
     * @param updatedCertificateDTO object with GiftCertificate data.
     * @param id GiftCertificate id.
     * @return object with GiftCertificate data.
     */
    GiftCertificateDTO updateGiftCertificate(GiftCertificateDTO updatedCertificateDTO, Integer id);

    /**
     * Accesses the corresponding DAO method to delete GiftCertificate object with specific id.
     *
     * @param id GiftCertificate id.
     */
    void deleteGiftCertificate(Integer id);

    /**
     * Accesses the corresponding DAO method to get all GiftCertificates
     * that have Tag with specific name.
     *
     * @return List of objects with GiftCertificate data.
     */
    List<GiftCertificateDTO> getGiftCertificatesByTagName(String name);

    /**
     * Accesses the corresponding DAO method to get GiftCertificates that matches parameters.
     *
     * @param giftCertificateCompositeParameter special object containing requested parameters.
     * @return list of GiftCertificates.
     */
    List<GiftCertificateDTO> getGiftCertificates(GiftCertificateCompositeParameter giftCertificateCompositeParameter);
}

