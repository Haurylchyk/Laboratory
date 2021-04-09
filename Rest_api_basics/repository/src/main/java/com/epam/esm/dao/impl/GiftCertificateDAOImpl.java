package com.epam.esm.dao.impl;

import com.epam.esm.constant.Variable;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.dao.query.GiftCertificateCompositeParameter;
import com.epam.esm.dao.query.GiftCertificateCompositeQuery;
import com.epam.esm.dao.query.builder.GiftCertificateQueryBuilder;
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

    private static final String CREATE_CERTIFICATE = "INSERT INTO gift_certificate (name, description, price, duration, create_date) " +
            "VALUES (:name, :description, :price, :duration, :create_date)";
    private static final String GET_CERTIFICATE_BY_ID = "SELECT * FROM gift_certificate WHERE (id=:id)";
    private static final String DELETE_CERTIFICATE = "DELETE FROM gift_certificate WHERE id = ?";
    private static final String GET_ALL_CERTIFICATES = "SELECT * FROM gift_certificate";
    private static final String GET_CERTIFICATES_BY_TAG_NAME = "SELECT gift_certificate.id, gift_certificate.name, description, " +
            "price, duration, create_date, last_update_date FROM gift_certificate " +
            "JOIN certificate_tag cert_tag ON gift_certificate.id = cert_tag.cert_id " +
            "JOIN tag ON tag.id = cert_tag.tag_id WHERE tag.name =:name";
    private static final String CREATE_CERTIFICATE_TAG = "INSERT INTO certificate_tag(cert_id, tag_id) VALUES (?,?)";
    private static final String DELETE_CERTIFICATE_TAGS_BY_CERTIFICATE_ID = "DELETE FROM certificate_tag WHERE (cert_id = ?)";

    /**
     * Object responsible for data (database) access.
     */
    private final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Object that links the GiftCertificate entity to the ResultSet.
     */
    private final static GiftCertificateMapper giftCertificateMapper = GiftCertificateMapper.getInstance();

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
    public GiftCertificate create(GiftCertificate giftCertificate) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        final Timestamp CURRENT_DATE = Timestamp.from(Instant.now());

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Variable.NAME, giftCertificate.getName());
        params.addValue(Variable.DESCRIPTION, giftCertificate.getDescription());
        params.addValue(Variable.PRICE, giftCertificate.getPrice());
        params.addValue(Variable.DURATION, giftCertificate.getDuration());
        params.addValue(Variable.CREATE_DATE, CURRENT_DATE);
        jdbcTemplate.update(CREATE_CERTIFICATE, params, keyHolder, new String[]{Variable.ID});

        Integer id = keyHolder.getKey().intValue();
        return read(id).get();
    }

    /**
     * Returns GiftCertificate with specific id.
     *
     * @param id GiftCertificate id.
     * @return Optional of GiftCertificate entity stored in the database.
     */
    @Override
    public Optional<GiftCertificate> read(Integer id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Variable.ID, id);
        List<GiftCertificate> listGiftCertificate = jdbcTemplate.query(GET_CERTIFICATE_BY_ID, params, giftCertificateMapper);
        return listGiftCertificate.isEmpty() ? Optional.empty() : Optional.of(listGiftCertificate.get(0));
    }

    /**
     * Updates GiftCertificate with specific id.
     *
     * @param giftCertificate updated object of the GiftCertificate type.
     * @param id              GiftCertificate id.
     * @return updated GiftCertificate entity.
     */
    @Override
    public GiftCertificate update(GiftCertificate giftCertificate, Integer id) {
        GiftCertificateQueryBuilder queryBuilder = GiftCertificateQueryBuilder.getInstance();
        GiftCertificateCompositeQuery compositeQuery = queryBuilder.buildUpdateQuery(giftCertificate);

        jdbcTemplate.getJdbcOperations().update(compositeQuery.getQuery(), compositeQuery.getParams());
        return read(id).get();
    }

    /**
     * Deletes GiftCertificate with specific id from database.
     *
     * @param id GiftCertificate id.
     */
    @Override
    public void delete(Integer id) {
        jdbcTemplate.getJdbcOperations().update(DELETE_CERTIFICATE, id);
    }

    /**
     * Returns all GiftCertificates stored in the database.
     *
     * @return all GiftCertificate stored in the database.
     */
    @Override
    @Transactional
    public List<GiftCertificate> getAllGiftCertificates() {
        List<GiftCertificate> giftCertificates = jdbcTemplate.query(GET_ALL_CERTIFICATES, giftCertificateMapper);
        return giftCertificates;
    }

    /**
     * Returns list of matching GiftCertificates.
     *
     * @param giftCertificateCompositeParameter special object containing params.
     * @return list of GiftCertificates.
     */
    @Override
    public List<GiftCertificate> getGiftCertificates(GiftCertificateCompositeParameter giftCertificateCompositeParameter) {

        GiftCertificateCompositeQuery giftCertificateCompositeQuery = GiftCertificateQueryBuilder.getInstance()
                .buildGetQuery(giftCertificateCompositeParameter);
        return jdbcTemplate.getJdbcOperations().query(giftCertificateCompositeQuery.getQuery(), giftCertificateCompositeQuery.getParams(), giftCertificateMapper);
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
        return jdbcTemplate.query(GET_CERTIFICATES_BY_TAG_NAME, params, giftCertificateMapper);
    }

    /**
     * Makes an record (to the database) that associates
     * specific GiftCertificate with specific Tag.
     *
     * @param giftCertificateId GiftCertificate id.
     * @param tagId             Tag id.
     */
    @Override
    public void putCertificateTag(Integer giftCertificateId, Integer tagId) {
        jdbcTemplate.getJdbcOperations().update(CREATE_CERTIFICATE_TAG, giftCertificateId, tagId);
    }

    /**
     * Deletes records (from the database) that associates
     * specific GiftCertificate with its Tags.
     *
     * @param id GiftCertificate id.
     */
    @Override
    public void deleteCertificateTagsById(Integer id) {
        jdbcTemplate.getJdbcOperations().update(DELETE_CERTIFICATE_TAGS_BY_CERTIFICATE_ID, id);
    }

}
