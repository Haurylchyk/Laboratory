package com.epam.esm;

import com.epam.esm.model.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface describes the service for working with OrderDTO.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public interface OrderService {

    /**
     * Accesses the corresponding DAO method to create a new Order object.
     *
     * @param userId                 id of the User who made the Order.
     * @param giftCertificatesIdList list of GiftCertificate IDs in the Order.
     * @return created object with Order data.
     */
    OrderDTO create(Integer userId, List<Integer> giftCertificatesIdList);

    /**
     * Accesses the corresponding DAO method to find Order object with specific id.
     *
     * @param id parameter containing the user id and the list of GiftCertificates IDs.
     * @return object with Order data.
     */
    OrderDTO findById(Integer id);

    /**
     * Accesses the corresponding DAO method to find all Orders.
     *
     * @param pageable object contains page number and page size.
     * @return List of objects with Order data.
     */
    Page<OrderDTO> findAll(Pageable pageable);

    /**
     * Accesses the corresponding DAO method to find all Orders
     * for User with a specific id.
     *
     * @param id       User id.
     * @param pageable object contains page number and page size.
     * @return List of objects with Order data.
     */
    Page<OrderDTO> findByUserId(Integer id, Pageable pageable);
}
