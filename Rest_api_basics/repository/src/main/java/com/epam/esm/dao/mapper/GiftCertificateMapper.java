package com.epam.esm.dao.mapper;

import com.epam.esm.constant.ParameterNameСonstant;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class is implementation interface RowMapper. Associates GiftCertificate entity with ResultSet.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {

    /**
     * Instance of this class.
     */
    private final static GiftCertificateMapper instance = new GiftCertificateMapper();

    /**
     * Private constructor.
     */
    private GiftCertificateMapper() {
    }

    /**
     * Returns instance of the class.
     *
     * @return instance of GiftCertificateMapper.
     */
    public static GiftCertificateMapper getInstance() {
        return instance;
    }

    /**
     * Associates ResultSet with GiftCertificate entity.
     *
     * @param rs     object of the ResultSet type.
     * @param rowNum row id.
     * @return GiftCertificate entity from database.
     * @throws SQLException
     */
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(rs.getInt(ParameterNameСonstant.ID));
        giftCertificate.setName(rs.getString(ParameterNameСonstant.NAME));
        giftCertificate.setDescription(rs.getString(ParameterNameСonstant.DESCRIPTION));
        giftCertificate.setPrice(rs.getInt(ParameterNameСonstant.PRICE));
        giftCertificate.setDuration(rs.getInt(ParameterNameСonstant.DURATION));
        giftCertificate.setCreateDate(rs.getTimestamp(ParameterNameСonstant.CREATE_DATE).toLocalDateTime());
        giftCertificate.setLastUpdateDate(rs.getTimestamp(ParameterNameСonstant.LAST_UPDATE_DATE).toLocalDateTime());

        return giftCertificate;
    }
}
