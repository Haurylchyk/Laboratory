package com.epam.esm.dao.query.builder;

import com.epam.esm.dao.query.QueryAndParam;
import com.epam.esm.dao.query.GiftCertificateParam;
import com.epam.esm.dao.query.filter.Filter;
import com.epam.esm.dao.query.sort.SortOrder;
import com.epam.esm.dao.query.sort.SortType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static final String START_SELECT_QUERY = "SELECT DISTINCT e FROM GiftCertificate e ";
    private static final String JOIN_TAG_TABLE = "INNER JOIN e.tagList t ";
    private static final String WHERE_STATEMENT = "WHERE ";
    private static final String NAME_REGEXP_STATEMENT = "e.name LIKE :nameRegex ";
    private static final String DESCRIPTION_REGEXP_STATEMENT = "e.description LIKE :descriptionRegex";
    private static final String TAG_NAME_STATEMENT = "t.name IN (:tagNames)";
    private static final String NAME_FIELD = "e.name";
    private static final String PRICE_FIELD = " e.price ";
    private static final String DURATION_FIELD = " e.duration ";
    private static final String TAG_NAMES_PARAM = "tagNames";
    private static final String NAME_PARAM = "nameRegex";
    private static final String DESCR_PARAM = "descriptionRegex";
    private static final String GROUP_AND_HEAVING_STATEMENT = " GROUP BY 1 HAVING COUNT(t.name) = ";
    private static final String ORDER_BY_STATEMENT = " ORDER BY ";
    private static final String LAST_UPDATE_DATE = "e.lastUpdateDate";
    private static final String ORDER_ASC = " ASC";
    private static final String ORDER_DESC = " DESC";
    private static final String SPLIT_STATEMENT = " AND ";

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
        List<String> whereList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        boolean isUsed = false;
        boolean isUsedTagList = false;
        int tagListSize = 0;
        queryBuilder.append(START_SELECT_QUERY);

        List<String> tagNameList = giftCertificateQueryParameter.getTagName();
        if (tagNameList != null) {
            isUsed = true;
            isUsedTagList = true;
            queryBuilder.append(JOIN_TAG_TABLE);
            whereList.add(TAG_NAME_STATEMENT);
            params.put(TAG_NAMES_PARAM, tagNameList);
            tagListSize = tagNameList.size();
        }

        String name = giftCertificateQueryParameter.getName();
        if (name != null) {
            isUsed = true;
            whereList.add(NAME_REGEXP_STATEMENT);
            params.put(NAME_PARAM, produceRegex(name));
        }

        String description = giftCertificateQueryParameter.getDescription();
        if (description != null) {
            isUsed = true;
            whereList.add(DESCRIPTION_REGEXP_STATEMENT);
            params.put(DESCR_PARAM, produceRegex(description));
        }

        List<Filter> priceFilterList = giftCertificateQueryParameter.getPrice();
        if (priceFilterList != null) {
            isUsed = true;
            for (Filter priceFilter : priceFilterList) {
                String priceParam = PRICE_FIELD + priceFilter.getType().getOperation() + priceFilter.getValue();
                whereList.add(priceParam);
            }
        }

        List<Filter> durationFilterList = giftCertificateQueryParameter.getDuration();
        if (durationFilterList != null) {
            isUsed = true;
            for (Filter priceFilter : durationFilterList) {
                String durationParam = DURATION_FIELD + priceFilter.getType().getOperation() + priceFilter.getValue();
                whereList.add(durationParam);
            }
        }

        if (isUsed) {
            queryBuilder.append(WHERE_STATEMENT);
            queryBuilder.append(String.join(SPLIT_STATEMENT, whereList));
            if (isUsedTagList) {
                queryBuilder.append(GROUP_AND_HEAVING_STATEMENT);
                queryBuilder.append(tagListSize);
            }
        }

        SortType sortType = giftCertificateQueryParameter.getSortType();
        if (sortType != null) {
            queryBuilder.append(ORDER_BY_STATEMENT);

            if (sortType == SortType.NAME) {
                queryBuilder.append(NAME_FIELD);
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

        return new QueryAndParam(queryBuilder.toString(), params);
    }

    private String produceRegex(String str) {
        return "%" + str + "%";

    }
}
