package com.epam.esm;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.OrderParamDTO;

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
     * @param orderParamDTO .
     * @return created object with Order data.
     */
    OrderDTO create(OrderParamDTO orderParamDTO);

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
     * @return List of objects with Order data.
     */
    List<OrderDTO> findAll();

    /**
     * Accesses the corresponding DAO method to find all Orders
     * for User with a specific id.
     *
     * @param id User id.
     * @return List of objects with Order data.
     */
    List<OrderDTO> findByUserId(int id);

}
