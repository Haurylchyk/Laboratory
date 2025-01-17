package com.epam.esm;

import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.GiftCertificateParamDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    GiftCertificateDTO create(GiftCertificateDTO giftCertificateDTO);

    /**
     * Accesses the corresponding DAO method to find GiftCertificate object with specific id.
     *
     * @param id GiftCertificate id.
     * @return object with GiftCertificate data.
     */
    GiftCertificateDTO findById(Integer id);

    /**
     * Accesses the corresponding DAO method to update GiftCertificate with specific id.
     *
     * @param updatedCertificateDTO object with GiftCertificate data.
     * @param id                    GiftCertificate id.
     * @return object with GiftCertificate data.
     */
    GiftCertificateDTO update(GiftCertificateDTO updatedCertificateDTO, Integer id);

    /**
     * Accesses the corresponding DAO method to delete GiftCertificate object with specific id.
     *
     * @param id GiftCertificate id.
     */
    void delete(Integer id);

    /**
     * Accesses the corresponding DAO method to find GiftCertificates that matches parameters.
     *
     * @param pageable     object contains page number and page size..
     * @param parameterDTO special object containing requested parameters.
     * @return list of GiftCertificates for the current page.
     */
    Page<GiftCertificateDTO> findByParam(GiftCertificateParamDTO parameterDTO, Pageable pageable);
}

