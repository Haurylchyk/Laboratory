package com.epam.esm.dao.query.filter;

import java.util.Objects;

public class Filter {

    private FilterType type;
    private int value;

    public FilterType getType() {
        return type;
    }

    public void setType(FilterType type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filter filter = (Filter) o;
        return value == filter.value &&
                type == filter.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }

    @Override
    public String toString() {
        return "Filter{" +
                "type=" + type +
                ", value=" + value +
                '}';
    }
}
