package com.epam.esm.dao.impl;

import com.epam.esm.constant.ParamNameConstant;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Class implements interface OrderDAO. Describes the interaction with the database
 * for operating with objects of the Order type.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
@Repository
public class OrderDAOImpl extends EntityDAOImpl<Order> implements OrderDAO {

    private static final String FIND_ORDERS_BY_USER_ID = "SELECT DISTINCT e FROM Order e INNER JOIN e.user c WHERE c.id = :id";

    /**
     * Returns all Orders for user with a specific id.
     *
     * @param id User id.
     * @param page number of page.
     * @param size number of Orders on page.
     * @return list of Orders.
     */
    public List<Order> findOrdersByUserId(Integer id, Integer page, Integer size) {
        return em.createQuery(FIND_ORDERS_BY_USER_ID, Order.class)
                .setParameter(ParamNameConstant.ID, id)
                .setMaxResults(size)
                .setFirstResult(size * (page - 1))
                .getResultList();
    }
}
