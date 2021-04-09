package com.epam.esm.dao;

import com.epam.esm.dao.query.GiftCertificateCompositeParameter;
import com.epam.esm.dao.query.GiftCertificateCompositeQuery;
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
public interface GiftCertificateDAO extends EntityDAO<GiftCertificate> {

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

    /**
     * Returns list of matching GiftCertificates.
     *
     * @param giftCertificateCompositeParameter special object containing params.
     * @return list of GiftCertificates.
     */
    List<GiftCertificate> getGiftCertificates(GiftCertificateCompositeParameter giftCertificateCompositeParameter);
}
