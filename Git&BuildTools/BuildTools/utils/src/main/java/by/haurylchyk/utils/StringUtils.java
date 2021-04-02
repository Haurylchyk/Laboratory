package by.haurylchyk.utils;

import static org.apache.commons.lang3.StringUtils.contains;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class StringUtils {
    private static final String MINUS = "-";
    private StringUtils(){}

    public static boolean isPositiveNumber(String number) {
        return isNumeric(number) && !contains(number, MINUS);
    }
}
