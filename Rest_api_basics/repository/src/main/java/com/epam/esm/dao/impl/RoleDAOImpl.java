package com.epam.esm.dao.impl;

import com.epam.esm.constant.ParamNameConstant;
import com.epam.esm.dao.RoleDAO;
import com.epam.esm.entity.Role;
import org.springframework.stereotype.Repository;

/**
 * Class implements interface TagDAO. Describes the interaction with the database
 * for operating with objects of the Role type.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
@Repository
public class RoleDAOImpl extends EntityDAOImpl<Role> implements RoleDAO {

    private static final String FIND_ROLE_BY_NAME = "SELECT DISTINCT e FROM Role e WHERE e.name = :name";

    public RoleDAOImpl() {
        super(Role.class);
    }

    /**
     * The index of the first item in the list.
     */
    private static final int FIRST_ELEMENT_INDEX = 0;

    /**
     * Returns Tag with specific name.
     *
     * @param name Tag name.
     * @return Optional of Tag entity stored in the database.
     */
    @Override
    public Role findByName(String name) {
        return em.createQuery(FIND_ROLE_BY_NAME, Role.class).
                setParameter(ParamNameConstant.NAME, name).getResultList().get(FIRST_ELEMENT_INDEX);
    }
}
