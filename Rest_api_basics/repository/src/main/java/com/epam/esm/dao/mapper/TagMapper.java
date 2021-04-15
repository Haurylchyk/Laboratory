package com.epam.esm.dao.mapper;

import com.epam.esm.constant.ParameterNameСonstant;
import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class is implementation interface RowMapper. Associates Tag entity with ResultSet.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public class TagMapper implements RowMapper<Tag> {

    /**
     * Instance of this class.
     */
    private final static TagMapper instance = new TagMapper();

    /**
     * Private constructor.
     */
    private TagMapper() {
    }

    /**
     * Returns instance of the class.
     *
     * @return instance of TagMapper.
     */
    public static TagMapper getInstance() {
        return instance;
    }

    /**
     * Associates ResultSet with Tag entity.
     *
     * @param rs     object of the ResultSet type.
     * @param rowNum row id.
     * @return Tag entity from database.
     * @throws SQLException
     */
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tag tag = new Tag();
        tag.setId(rs.getInt(ParameterNameСonstant.ID));
        tag.setName(rs.getString(ParameterNameСonstant.NAME));
        return tag;
    }

}
