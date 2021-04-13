package com.epam.esm.dao.query.builder;

import com.epam.esm.dao.query.QueryAndParam;
import com.epam.esm.dao.query.GiftCertificateParam;
import com.epam.esm.dao.query.sort.SortOrder;
import com.epam.esm.dao.query.sort.SortType;
import com.epam.esm.entity.GiftCertificate;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Class is used to convert parameters obtained
 * from a HTTP request to an SQL query.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public class GiftCertificateQueryBuilder {

    private static final GiftCertificateQueryBuilder instance = new GiftCertificateQueryBuilder();

    private static final String START_SELECT_QUERY = "SELECT * from gift_certificate ";
    private static final String JOIN_TAG_TABLE = "JOIN certificate_tag ct ON " +
            "gift_certificate.id = ct.cert_id JOIN tag ON ct.tag_id = tag.id ";
    private static final String WHERE_STATEMENT = "WHERE (";
    private static final String NAME_REGEXP_PARAM = "gift_certificate.name REGEXP ?";
    private static final String DESCRIPTION_REGEXP_PARAM = "description REGEXP ?";
    private static final String TAG_NAME_PARAM = "tag.name = ?";
    private static final String CLOSE_WHERE = ")";
    private static final String ORDER_BY_STATEMENT = " ORDER BY ";
    private static final String NAME = "gift_certificate.name";
    private static final String LAST_UPDATE_DATE = "gift_certificate.last_update_date";
    private static final String ORDER_ASC = " ASC";
    private static final String ORDER_DESC = " DESC";
    private static final String SPLIT_STATEMENT = " AND ";
    private static final String START_UPDATE_QUERY = "UPDATE gift_certificate SET ";
    private static final String NAME_PARAM = "name = ?";
    private static final String DESCRIPTION_PARAM = "description = ?";
    private static final String PRICE_PARAM = "price = ?";
    private static final String DURATION_PARAM = "duration = ?";
    private static final String END_UPDATE_QUERY = " WHERE id = ?";
    private static final String LAST_UPDATE_DATE_PARAM = "last_update_date = ?";
    private static final String COMMA = ", ";

    private GiftCertificateQueryBuilder() {
    }

    /**
     * Returns instance of this class.
     *
     * @return Instance of GiftCertificateQueryBuilder.
     */
    public static GiftCertificateQueryBuilder getInstance() {
        return instance;
    }

    /**
     * Converts parameters from request into SQL query and array of parameters.
     *
     * @param giftCertificateQueryParameter object with parameters from request.
     * @return object, that contains SQL query and array of parameters.
     */
    public QueryAndParam buildGetQuery(GiftCertificateParam giftCertificateQueryParameter) {
        StringBuilder queryBuilder = new StringBuilder();
        List<String> conditionList = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        boolean isUsed = false;

        queryBuilder.append(START_SELECT_QUERY);

        String tagName = giftCertificateQueryParameter.getTagName();
        if (tagName != null) {
            isUsed = true;
            queryBuilder.append(JOIN_TAG_TABLE);
            conditionList.add(TAG_NAME_PARAM);
            params.add(tagName);
        }

        String name = giftCertificateQueryParameter.getName();
        if (name != null) {
            isUsed = true;
            conditionList.add(NAME_REGEXP_PARAM);
            params.add(name);
        }

        String description = giftCertificateQueryParameter.getDescription();
        if (description != null) {
            isUsed = true;
            conditionList.add(DESCRIPTION_REGEXP_PARAM);
            params.add(description);
        }

        if (isUsed) {
            queryBuilder.append(WHERE_STATEMENT);
            queryBuilder.append(String.join(SPLIT_STATEMENT, conditionList));
            queryBuilder.append(CLOSE_WHERE);
        }

        SortType sortType = giftCertificateQueryParameter.getSortType();
        if (sortType != null) {
            queryBuilder.append(ORDER_BY_STATEMENT);

            if (sortType == SortType.NAME) {
                queryBuilder.append(NAME);
            } else if (sortType == SortType.DATE) {
                queryBuilder.append(LAST_UPDATE_DATE);
            }
        }

        SortOrder sortOrder = giftCertificateQueryParameter.getSortOrder();
        if (sortOrder != null) {
            switch (sortOrder) {
                case ASC: {
                    queryBuilder.append(ORDER_ASC);
                    break;
                }
                case DESC:
                    queryBuilder.append(ORDER_DESC);
                    break;
            }
        }

        return new QueryAndParam(queryBuilder.toString(), params.toArray());
    }

    /**
     * Converts parameters from request into SQL query and array of parameters.
     *
     * @param giftCertificate GiftCertificate entity.
     * @return object, that contains SQL query and array of parameters.
     */
    public QueryAndParam buildUpdateQuery(GiftCertificate giftCertificate) {
        List<String> conditionList = new ArrayList<>();
        List<Object> paramList = new ArrayList<>();

        String name = giftCertificate.getName();
        if (name != null) {
            conditionList.add(NAME_PARAM);
            paramList.add(name);
        }

        String description = giftCertificate.getDescription();
        if (description != null) {
            conditionList.add(DESCRIPTION_PARAM);
            paramList.add(description);
        }

        Integer price = giftCertificate.getPrice();
        if (price != null) {
            conditionList.add(PRICE_PARAM);
            paramList.add(price);
        }

        Integer duration = giftCertificate.getDuration();
        if (duration != null) {
            conditionList.add(DURATION_PARAM);
            paramList.add(duration);
        }

        conditionList.add(LAST_UPDATE_DATE_PARAM);
        paramList.add(Timestamp.from(Instant.now()));

        Integer id = giftCertificate.getId();
        paramList.add(id);

        Object[] paramArray = paramList.toArray();

        String paramsQuery = String.join(COMMA, conditionList);
        String query = START_UPDATE_QUERY + paramsQuery + END_UPDATE_QUERY;

        return new QueryAndParam(query, paramArray);
    }
}
