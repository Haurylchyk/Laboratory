package com.epam.esm.constant;

/**
 * Class includes all the SQL queries.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public class SqlQuery {

    //Queries for TagDAO
    public static final String CREATE_TAG = "INSERT INTO tag (name) VALUES (:name)";
    public static final String GET_TAG_BY_ID = "SELECT * FROM tag WHERE id =:id";
    public static final String DELETE_TAG = "DELETE FROM tag WHERE id = ?";
    public static final String GET_ALL_TAGS = "SELECT * FROM tag";
    public static final String GET_TAG_BY_NAME = "SELECT * FROM tag WHERE name =:name";
    public static final String GET_TAGS_BY_CERTIFICATE_ID = "SELECT id, name FROM tag JOIN certificate_tag AS cert_tag ON " +
            "tag.id = cert_tag.tag_id WHERE cert_tag.cert_id =:id";
    public static final String CREATE_CERTIFICATE_TAG = "INSERT INTO certificate_tag(cert_id, tag_id) VALUES (?,?)";

    //Queries for GiftCertificateDAO
    public static final String CREATE_CERTIFICATE = "INSERT INTO gift_certificate (name, description, price, duration, create_date) " +
            "VALUES (:name, :description, :price, :duration, :create_date)";
    public static final String GET_CERTIFICATE_BY_ID = "SELECT * FROM gift_certificate WHERE (id=:id)";
    public static final String UPDATE_CERTIFICATE = "UPDATE gift_certificate SET " +
            "name =:name, description =:description, price =:price, duration =:duration, last_update_date =:last_update_date WHERE id =:id";
    public static final String DELETE_CERTIFICATE = "DELETE FROM gift_certificate WHERE id = ?";
    public static final String GET_ALL_CERTIFICATES = "SELECT * FROM gift_certificate";
    public static final String GET_CERTIFICATES_BY_TAG_NAME = "SELECT gift_certificate.id, gift_certificate.name, description, " +
            "price, duration, create_date, last_update_date FROM gift_certificate " +
            "JOIN certificate_tag cert_tag ON gift_certificate.id = cert_tag.cert_id " +
            "JOIN tag ON tag.id = cert_tag.tag_id WHERE tag.name =:name";
    public static final String DELETE_CERTIFICATE_TAGS_BY_CERTIFICATE_ID = "DELETE FROM certificate_tag WHERE (cert_id = ?)";





}
