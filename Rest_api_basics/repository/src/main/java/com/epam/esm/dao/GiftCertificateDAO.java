package com.epam.esm.dao;


import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

/**
 * Interface describes the interaction with the database
 * for operating with objects of the GiftCertificate type.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public interface GiftCertificateDAO {

    /**
     * Adds new object GiftCertificate to database.
     *
     * @param giftCertificate object of the GiftCertificate type.
     * @return GiftCertificate entity.
     */
    GiftCertificate createGiftCertificate(GiftCertificate giftCertificate);

    /**
     * Returns GiftCertificate with specific id.
     *
     * @param id GiftCertificate id.
     * @return Optional of GiftCertificate entity stored in the database.
     */
    Optional<GiftCertificate> getGiftCertificateById(Integer id);

    /**
     * Updates GiftCertificate with specific id.
     *
     * @param updatedGiftCertificate updated GiftCertificate.
     * @param id                 GiftCertificate id.
     * @return updated GiftCertificate entity.
     */
    GiftCertificate updateGiftCertificate(GiftCertificate updatedGiftCertificate, Integer id);

    /**
     * Deletes GiftCertificate with specific id from database.
     *
     * @param id GiftCertificate id.
     */
    void deleteGiftCertificate(Integer id);

    /**
     * Returns all GiftCertificates stored in the database.
     *
     * @return all GiftCertificate stored in the database.
     */
    List<GiftCertificate> getAllGiftCertificates();

    /**
     * Returns GiftCertificates that have Tag with specific name.
     *
     * @return list of GiftCertificates.
     */
    List<GiftCertificate> getGiftCertificatesByTagName(String name);

    /**
     * Makes an record (to the database) that associates
     * specific GiftCertificate with specific Tag.
     *
     * @param giftCertificateId GiftCertificate id.
     * @param tagId  Tag id.
     */
    void putCertificateTag(Integer giftCertificateId, Integer tagId);

    /**
     * Deletes records (from the database) that associates
     * specific GiftCertificate with its Tags.
     *
     * @param id GiftCertificate id.
     */
    void deleteCertificateTagsById(Integer id);
}
