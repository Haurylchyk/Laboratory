package com.epam.esm.dao.query.filter;

public enum FilterType {

    EQ("="),
    LT ("<"),
    LTE("<="),
    GT(">"),
    GTE(">=");

    private String operation;

    FilterType(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
