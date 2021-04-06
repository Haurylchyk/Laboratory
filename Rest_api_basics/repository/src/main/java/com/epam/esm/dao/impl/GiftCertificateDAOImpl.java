package com.epam.esm.dao.impl;

import com.epam.esm.constant.SqlQuery;
import com.epam.esm.constant.Variable;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Class implements interface GiftCertificateDAO. Describes the interaction with the database
 * for operating with objects of the GiftCertificate type.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
@Repository
public class GiftCertificateDAOImpl implements GiftCertificateDAO {

    /**
     * Object of the NamedParameterJdbcTemplate type.
     */
    private final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Object of the GiftCertificateMapper type.
     */
    private final static GiftCertificateMapper giftCertificateMapper = GiftCertificateMapper.getInstance();

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
    public GiftCertificateDAOImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * Adds new object GiftCertificate to database.
     *
     * @param giftCertificate object of the GiftCertificate type.
     * @return GiftCertificate entity.
     */
    @Override
    public GiftCertificate createGiftCertificate(GiftCertificate giftCertificate) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        final Timestamp CURRENT_DATE = Timestamp.from(Instant.now());

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Variable.NAME, giftCertificate.getName());
        params.addValue(Variable.DESCRIPTION, giftCertificate.getDescription());
        params.addValue(Variable.PRICE, giftCertificate.getPrice());
        params.addValue(Variable.DURATION, giftCertificate.getDuration());
        params.addValue(Variable.CREATE_DATE, CURRENT_DATE);
        jdbcTemplate.update(SqlQuery.CREATE_CERTIFICATE, params, keyHolder, new String[]{Variable.ID});

        Integer id = keyHolder.getKey().intValue();
        return getGiftCertificateById(id).get();
    }

    /**
     * Returns GiftCertificate with specific id.
     *
     * @param id GiftCertificate id.
     * @return Optional of GiftCertificate entity stored in the database.
     */
    @Override
    public Optional<GiftCertificate> getGiftCertificateById(Integer id) {
        final int FIRST_INDEX = 0;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Variable.ID, id);
        List<GiftCertificate> listGiftCertificate = jdbcTemplate.query(SqlQuery.GET_CERTIFICATE_BY_ID, params, giftCertificateMapper);
        return listGiftCertificate.isEmpty() ? Optional.empty() : Optional.of(listGiftCertificate.get(FIRST_INDEX));
    }

    /**
     * Updates GiftCertificate with specific id.
     *
     * @param updatedGiftCertificate updated GiftCertificate.
     * @param id                 GiftCertificate id.
     * @return updated GiftCertificate entity.
     */
    @Override
    public GiftCertificate updateGiftCertificate(GiftCertificate updatedGiftCertificate, Integer id) {
        final Timestamp CURRENT_DATE = Timestamp.from(Instant.now());

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Variable.ID, id);
        params.addValue(Variable.NAME, updatedGiftCertificate.getName());
        params.addValue(Variable.DESCRIPTION, updatedGiftCertificate.getDescription());
        params.addValue(Variable.PRICE, updatedGiftCertificate.getPrice());
        params.addValue(Variable.DURATION, updatedGiftCertificate.getDuration());
        params.addValue(Variable.LAST_UPDATE_DATE, CURRENT_DATE);
        jdbcTemplate.update(SqlQuery.UPDATE_CERTIFICATE, params);

        return getGiftCertificateById(id).get();
    }

    /**
     * Deletes GiftCertificate with specific id from database.
     *
     * @param id GiftCertificate id.
     */
    @Override
    public void deleteGiftCertificate(Integer id) {
        jdbcTemplate.getJdbcOperations().update(SqlQuery.DELETE_CERTIFICATE, id);
    }

    /**
     * Returns all GiftCertificates stored in the database.
     *
     * @return all GiftCertificate stored in the database.
     */
    @Override
    @Transactional
    public List<GiftCertificate> getAllGiftCertificates() {
        List<GiftCertificate> giftCertificates = jdbcTemplate.query(SqlQuery.GET_ALL_CERTIFICATES, giftCertificateMapper);
        return giftCertificates;
    }

    /**
     * Returns GiftCertificates that have Tag with specific name.
     *
     * @return list of GiftCertificates.
     */
    @Override
    public List<GiftCertificate> getGiftCertificatesByTagName(String name) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Variable.NAME, name);
        return jdbcTemplate.query(SqlQuery.GET_CERTIFICATES_BY_TAG_NAME, params, giftCertificateMapper);
    }

    /**
     * Makes an record (to the database) that associates
     * specific GiftCertificate with specific Tag.
     *
     * @param giftCertificateId GiftCertificate id.
     * @param tagId  Tag id.
     */
    @Override
    public void putCertificateTag(Integer giftCertificateId, Integer tagId) {
        jdbcTemplate.getJdbcOperations().update(SqlQuery.CREATE_CERTIFICATE_TAG, giftCertificateId, tagId);
    }

    /**
     * Deletes records (from the database) that associates
     * specific GiftCertificate with its Tags.
     *
     * @param id GiftCertificate id.
     */
    @Override
    public void deleteCertificateTagsById(Integer id) {
        jdbcTemplate.getJdbcOperations().update(SqlQuery.DELETE_CERTIFICATE_TAGS_BY_CERTIFICATE_ID, id);
    }
}
