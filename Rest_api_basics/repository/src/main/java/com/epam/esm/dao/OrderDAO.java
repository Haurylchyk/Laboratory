package com.epam.esm.dao;

import com.epam.esm.entity.Order;

import java.util.List;

/**
 * Interface describes the interaction with the database
 * for operating with objects of the Order type.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public interface OrderDAO extends EntityDAO<Order> {

    /**
     * Returns all Orders stored in the database.
     *
     * @return all Orders stored in the database.
     */
    List<Order> findAll();

    /**
     * Returns all Orders for User with a specific id.
     *
     * @param id User id.
     * @return list of Orders.
     */
    List<Order> findOrdersByUserId(Integer id);
}
