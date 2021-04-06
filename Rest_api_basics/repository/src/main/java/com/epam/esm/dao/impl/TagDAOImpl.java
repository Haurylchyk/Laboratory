package com.epam.esm.dao.impl;

import com.epam.esm.constant.SqlQuery;
import com.epam.esm.constant.Variable;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

/**
 * Class implements interface TagDAO. Describes the interaction with the database
 * for operating with objects of the Tag type.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
@Repository
public class TagDAOImpl implements TagDAO {

    /**
     * Object of the NamedParameterJdbcTemplate type.
     */
    private final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Object of the TagMapper type.
     */
    private final static TagMapper tagMapper = TagMapper.getInstance();

    /**
     * Constructor with parameter.
     *
     * @param dataSource object that manages connections.
     */
    @Autowired
    public TagDAOImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * Adds new Tag to database.
     *
     * @param name Tag name
     * @return Tag entity.
     */
    @Override
    public Tag createTag(String name) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Variable.NAME, name);
        jdbcTemplate.update(SqlQuery.CREATE_TAG, params);
        return getTagByName(name).get();
    }

    /**
     * Returns Tag with specific id.
     *
     * @param id Tag id.
     * @return Optional of Tag entity stored in the database.
     */
    @Override
    public Optional<Tag> getTagById(Integer id) {

        Optional<Tag> optional = Optional.empty();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Variable.ID, id);
        Tag tag = jdbcTemplate.queryForObject(SqlQuery.GET_TAG_BY_ID, params, tagMapper);
        if (tag != null) {
            optional = Optional.of(tag);
        }
        return optional;
    }

    /**
     * Deletes Tag with specific id from database.
     *
     * @param id Tag id.
     */
    @Override
    public void deleteTag(Integer id) {
        jdbcTemplate.getJdbcOperations().update(SqlQuery.DELETE_TAG, id);
    }

    /**
     * Returns all Tags stored in the database.
     *
     * @return all Tags stored in the database.
     */
    @Override
    public List<Tag> getAllTags() {
        return jdbcTemplate.query(SqlQuery.GET_ALL_TAGS, tagMapper);
    }

    /**
     * Returns Tag with specific name.
     *
     * @param name Tag name.
     * @return Optional of Tag entity stored in the database.
     */
    @Override
    public Optional<Tag> getTagByName(String name) {

        Optional<Tag> optional = Optional.empty();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Variable.NAME, name);
        Tag tag = jdbcTemplate.queryForObject(SqlQuery.GET_TAG_BY_NAME, params, tagMapper);
        if (tag != null) {
            optional = Optional.of(tag);
        }
        return optional;
    }

    /**
     * Returns list of Tags of GiftCertificate with specific id.
     *
     * @param id GiftCertificate id.
     * @return List of Tags.
     */
    @Override
    public List<Tag> getTagsByGiftCertificateId(Integer id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Variable.ID, id);
        return jdbcTemplate.query(SqlQuery.GET_TAGS_BY_CERTIFICATE_ID, params, tagMapper);
    }
}
