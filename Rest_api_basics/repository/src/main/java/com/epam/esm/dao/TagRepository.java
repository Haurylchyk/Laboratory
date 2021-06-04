package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Interface describes the interaction with the database
 * for operating with objects of the Tag type.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public interface TagRepository extends JpaRepository<Tag, Integer> {

    /**
     * Returns Tag with specific name.
     *
     * @param name Tag name.
     * @return Optional of Tag entity stored in the database.
     */
    Optional<Tag> findByName(String name);

    /**
     * Returns list of Tags of GiftCertificate with specific id.
     *
     * @param id GiftCertificate id.
     * @return List of Tags.
     */
    @Query("SELECT e FROM Tag e INNER JOIN e.certificates c WHERE c.id = :id")
    List<Tag> findByGiftCertificateId(Integer id);

    /**
     * Returns the most widely used tag for
     * the user with with specific.
     *
     * @param id User id.
     * @param pageable object contains page number and page size.
     * @return a sorted list of tags for a specific page, in which
     * the most widely used tag for the user with specific is
     * in the first position.
     *
     */
    @Query("SELECT t FROM Order e INNER JOIN e.giftCertificateList c INNER JOIN c.giftCertificate.tagList t " +
            "INNER JOIN e.user u WHERE u.id = :id GROUP BY t.name ORDER BY COUNT(t) DESC")
    List<Tag> findMostWidelyUsedByUserId(Integer id, Pageable pageable);
}
