package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface describes the interaction with the database
 * for operating with objects of the Order type.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {

    /**
     * Returns all Orders for User with a specific id.
     *
     * @param id       User id.
     * @param pageable object contains page number and page size.
     * @return list of Orders.
     */
    Page<Order> findByUserId(Integer id, Pageable pageable);
}
