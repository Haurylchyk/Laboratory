package com.epam.esm.dao;

import com.epam.esm.dao.query.parameter.GiftCertificateParam;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * Interface describes the interaction with the database
 * for operating with objects of the GiftCertificate type.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Integer> {

    /**
     * Returns GiftCertificates that have Tag with specific name.
     *
     * @return list of GiftCertificates.
     */
    List<GiftCertificate> findByTagList_NameContaining(String name);

    /**
     * Returns list of matching GiftCertificates.
     *
     * @param pageNumber number of page.
     * @param size number of GiftCertificates on page.
     * @param giftCertificateParam special object containing params.
     * @return total count and list of GiftCertificates of current page.
     */
    Map<Integer, List<GiftCertificate>> findByParam(Integer pageNumber, Integer size, GiftCertificateParam giftCertificateParam);

}
