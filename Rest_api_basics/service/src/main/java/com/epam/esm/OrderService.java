package com.epam.esm;

import com.epam.esm.dto.OrderDTO;

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
    OrderDTO create(Integer userId,  List<Integer> giftCertificatesIdList);

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
     * @param pageNumber number of page.
     * @param size number of Orders on page.
     * @return List of objects with Order data.
     */
    List<OrderDTO> findAll(Integer pageNumber, Integer size);

    /**
     * Accesses the corresponding DAO method to find all Orders
     * for User with a specific id.
     *
     * @param id User id.
     * @return List of objects with Order data.
     */
    List<OrderDTO> findByUserId(int id);

    /**
     * Calculates the total number of pages required to display all Orders.
     *
     * @return the total number of pages required to display all Orders.
     */
    Integer findNumberPagesForAllOrders(Integer size);

}
