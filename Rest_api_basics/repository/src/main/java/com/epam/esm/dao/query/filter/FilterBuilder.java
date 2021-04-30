package com.epam.esm.dao.query.filter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FilterBuilder {

    private static final String REGEX = "([\\w]{2}):([0-9]*)";
    private static final int FIRST_GROUP = 1;
    private static final int SECOND_GROUP = 2;

    public static Filter build(String parameter) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(parameter);
        Filter filter = new Filter();

        if (!matcher.matches()) {
            filter.setType(FilterType.EQ);
            filter.setValue(Integer.parseInt(parameter.toUpperCase()));

        } else {
            String filterTypeName = matcher.group(FIRST_GROUP).toUpperCase();
            filter.setType(FilterType.valueOf(filterTypeName));
            String stringFilterValue = matcher.group(SECOND_GROUP).toUpperCase();
            filter.setValue(Integer.parseInt(stringFilterValue));
        }
        return filter;
    }

    public static List<Filter> build(List<String> parameters) {
        return parameters.stream().map(param -> build(param)).collect(Collectors.toList());
    }
}
