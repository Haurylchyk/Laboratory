package com.epam.esm.dao.query;

import java.util.Arrays;
import java.util.Objects;

/**
 * Class describes an entity that contains an SQL query
 * and a set of parameters required to execute this query.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public class GiftCertificateCompositeQuery {
    private final String query;
    private final Object[] params;

    public GiftCertificateCompositeQuery(String query, Object[] params) {
        this.params = params;
        this.query = query;
    }

    public Object[] getParams() {
        return params;
    }

    public String getQuery() {
        return query;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificateCompositeQuery that = (GiftCertificateCompositeQuery) o;
        return Arrays.equals(params, that.params) &&
                Objects.equals(query, that.query);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(query);
        result = 31 * result + Arrays.hashCode(params);
        return result;
    }

    @Override
    public String toString() {
        return "GiftCertificateCompositeQuery{" +
                "params=" + Arrays.toString(params) +
                ", request='" + query + '\'' +
                '}';
    }
}
