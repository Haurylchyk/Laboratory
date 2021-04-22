package com.epam.esm.dao.impl;

import com.epam.esm.constant.ParamNameConstant;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.entity.Order;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Class implements interface OrderDAO. Describes the interaction with the database
 * for operating with objects of the Order type.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public class OrderDAOImpl implements OrderDAO {

    @PersistenceContext
    private EntityManager em;

    private static final String FIND_ORDER_BY_ID = "SELECT DISTINCT e FROM Order e WHERE e.id = :id";
    private static final String DELETE_ORDER = "DELETE FROM Order e WHERE e.id = :id";
    private static final String FIND_ALL_ORDERS = "SELECT DISTINCT e FROM Order e";
    private static final String FIND_ORDERS_BY_USER_ID = "SELECT DISTINCT e FROM Order e INNER JOIN e.user c WHERE c.id = :id";

    /**
     * The index of the first item in the list.
     */
    private static final int FIRST_ELEMENT_INDEX = 0;

    /**
     * Adds new Order to database.
     *
     * @param order Order entity to create.
     * @return Order entity.
     */
    @Override
    @Transactional
    public Order save(Order order) {
        em.persist(order);
        return find(order.getId()).get();
    }

    /**
     * Returns Order with specific id.
     *
     * @param id Order id.
     * @return Optional of Order entity stored in the database.
     */
    @Override
    public Optional<Order> find(Integer id) {
        List<Order> orderList = em.createQuery(FIND_ORDER_BY_ID, Order.class).
                setParameter(ParamNameConstant.ID, id).getResultList();
        return orderList.isEmpty() ? Optional.empty() : Optional.of(orderList.get(FIRST_ELEMENT_INDEX));
    }

    /**
     * Deletes Order with specific id from database.
     *
     * @param id Order id.
     */
    @Override
    @Transactional
    public void delete(Integer id) {
        em.createQuery(DELETE_ORDER).setParameter(ParamNameConstant.ID, id).
                executeUpdate();
    }

    /**
     * Returns all Orders stored in the database.
     *
     * @return all Orders stored in the database.
     */
    @Override
    public List<Order> findAll() {
        return em.createQuery(FIND_ALL_ORDERS, Order.class).getResultList();
    }

    /**
     * Returns all Orders for user with a specific id.
     *
     * @param id User id.
     * @return list of Orders.
     */
    public List<Order> findOrdersByUserId(Integer id) {
        return em.createQuery(FIND_ORDERS_BY_USER_ID, Order.class).getResultList();
    }
}
