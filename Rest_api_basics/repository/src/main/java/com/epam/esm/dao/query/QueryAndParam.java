package com.epam.esm.dao.query;

import java.util.Map;
import java.util.Objects;

/**
 * Class describes an entity that contains an SQL query
 * and a set of parameters required to execute this query.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public class QueryAndParam {
    private final String query;
    private final Map<String, Object> params;

    public QueryAndParam(String query, Map<String, Object> params) {
        this.params = params;
        this.query = query;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public String getQuery() {
        return query;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryAndParam that = (QueryAndParam) o;
        return query.equals(that.query) &&
                params.equals(that.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, params);
    }

    @Override
    public String toString() {
        return "QueryAndParam{" +
                "query='" + query + '\'' +
                ", params=" + params +
                '}';
    }
}
