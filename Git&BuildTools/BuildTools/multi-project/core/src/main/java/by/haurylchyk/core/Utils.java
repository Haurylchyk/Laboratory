package by.haurylchyk.core;

import static by.haurylchyk.utils.StringUtils.isPositiveNumber;

public class Utils {
    public static boolean isAllPositiveNumbers(String... str) {
        for (String number : str) {
            if (!isPositiveNumber(number)) {
                return false;
            }
        }
        return true;
    }
}
