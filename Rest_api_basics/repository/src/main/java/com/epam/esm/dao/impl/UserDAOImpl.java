package com.epam.esm.dao.impl;

import com.epam.esm.constant.ParamNameConstant;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Repository;

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
public class UserDAOImpl extends EntityDAOImpl<User> implements UserDAO {

    private static final String FIND_USER_BY_LOGIN = "SELECT DISTINCT e FROM User e WHERE e.login = :login";
    private static final String FIND_USER_WITH_TOP_ORDERS = "SELECT u FROM Order o JOIN o.user u GROUP BY u ORDER BY SUM(o.cost) DESC";

    public UserDAOImpl() {
        super(User.class);
    }

    /**
     * The index of the first item in the list.
     */
    private static final int FIRST_ELEMENT_INDEX = 0;

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
    public Optional<User> findUserWithTopOrders() {
        Query query = em.createQuery(FIND_USER_WITH_TOP_ORDERS, User.class);
        List<User> userList = query.getResultList();
        return userList.isEmpty() ? Optional.empty() : Optional.of(userList.get(FIRST_ELEMENT_INDEX));
    }
}
