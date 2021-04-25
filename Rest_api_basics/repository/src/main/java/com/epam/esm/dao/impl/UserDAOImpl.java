package com.epam.esm.dao.impl;

import com.epam.esm.constant.ParamNameConstant;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

/**
 * Class implements interface UserDAO. Describes the interaction with the database
 * for operating with objects of the User type.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager em;

    private static final String FIND_USER_BY_ID = "SELECT DISTINCT e FROM User e WHERE e.id = :id";
    private static final String DELETE_USER = "DELETE FROM User e WHERE e.id = :id";
    private static final String FIND_ALL_USERS = "SELECT DISTINCT e FROM User e";
    private static final String FIND_USER_BY_LOGIN = "SELECT DISTINCT e FROM User e WHERE e.login = :login";
    private static final String FIND_USER_WITH_HIGHEST_ORDER_AMOUNT = "SELECT u FROM User u JOIN u.orderList o GROUP BY u ORDER BY SUM(o.cost) DESC";

    /**
     * The index of the first item in the list.
     */
    private static final int FIRST_ELEMENT_INDEX = 0;

    /**
     * Adds new User to database.
     *
     * @param user User entity to create.
     * @return User entity.
     */
    @Override
    @Transactional
    public User save(User user) {
        em.persist(user);
        return find(user.getId()).get();
    }

    /**
     * Returns User with specific id.
     *
     * @param id User id.
     * @return Optional of User entity stored in the database.
     */
    @Override
    public Optional<User> find(Integer id) {
        List<User> userList = em.createQuery(FIND_USER_BY_ID, User.class).
                setParameter(ParamNameConstant.ID, id).getResultList();
        return userList.isEmpty() ? Optional.empty() : Optional.of(userList.get(FIRST_ELEMENT_INDEX));
    }

    /**
     * Deletes User with specific id from database.
     *
     * @param id User id.
     */
    @Override
    @Transactional
    public void delete(Integer id) {
        em.createQuery(DELETE_USER).setParameter(ParamNameConstant.ID, id).
                executeUpdate();
    }

    /**
     * Returns all Users stored in the database.
     *
     * @return all Users stored in the database.
     */
    @Override
    public List<User> findAll() {
        return em.createQuery(FIND_ALL_USERS, User.class).getResultList();
    }

    /**
     * Returns User with specific login.
     *
     * @param login User login.
     * @return Optional of User entity stored in the database.
     */
    @Override
    public Optional<User> findByLogin(String login) {
        List<User> userList = em.createQuery(FIND_USER_BY_LOGIN, User.class).
                setParameter(ParamNameConstant.LOGIN, login).getResultList();
        return userList.isEmpty() ? Optional.empty() : Optional.of(userList.get(FIRST_ELEMENT_INDEX));
    }

    /**
     * Returns the user with the highest order amount.
     *
     * @return the user with the highest order amount.
     */
    @Override
    public User findUserWithHighestAmountOrders() {
        Query query = em.createQuery(FIND_USER_WITH_HIGHEST_ORDER_AMOUNT, User.class);
        List<User> userList = query.getResultList();
        return userList.get(FIRST_ELEMENT_INDEX);
    }
}
