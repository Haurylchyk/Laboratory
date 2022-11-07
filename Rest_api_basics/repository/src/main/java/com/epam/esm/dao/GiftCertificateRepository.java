package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Interface describes the interaction with the database
 * for operating with objects of the GiftCertificate type.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Integer>,
        JpaSpecificationExecutor<GiftCertificate> {

    /**
     * Returns GiftCertificates that have Tag with specific name.
     *
     * @return list of GiftCertificates.
     */
    List<GiftCertificate> findByTagList_NameContaining(String name);

    /**
     * Returns GiftCertificate with the maximum price.
     *
     * @return GiftCertificate.
     */
    GiftCertificate findFirstByOrderByPriceDesc();

    /**
     * Returns GiftCertificate with the maximum duration.
     *
     * @return GiftCertificate.
     */
    GiftCertificate findFirstByOrderByDurationDesc();
}
