package com.epam.esm.validator;

import java.util.Collection;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class contains methods for checking  values of common
 * fields that describe the details of the entities.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public class CommonValidator {

    /**
     * Private constructor.
     */
    private CommonValidator() {
    }

    /**
     * The minimum permitted length of the string.
     */
    private static final int MIN_VALID_NAME_LENGTH = 2;

    /**
     * The maximum permitted length of the string.
     */
    private static final int MAX_VALID_NAME_LENGTH = 100;

    /**
     * Checks if the object is not empty.
     *
     * @param object object for verification.
     * @return true if the object is not empty, false otherwise.
     */
    public static boolean isEmpty(Object object) {
        if (!Optional.ofNullable(object).isPresent()) {
            return true;
        }
        if (object instanceof String && ((String) object).isEmpty()) {
            return true;
        }
        if (object instanceof Collection && ((Collection) object).isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the string meets the requirements.
     *
     * @param str string for verification.
     * @return true if the string meets the requirements, false otherwise.
     */
    public static boolean isStringLengthValid(String str) {

        return str.length() >= MIN_VALID_NAME_LENGTH
                && str.length() <= MAX_VALID_NAME_LENGTH;
    }

    /**
     * Checks that the number is positive.
     *
     * @param number number for verification.
     * @return true if the number meets the requirements, false otherwise.
     */
    public static boolean isPositiveNumber(Integer number) {
        return number > 0;
    }

    /**
     * Checks whether the name matches the specified format.
     *
     * @param name name for verification.
     * @return true if the name meets the requirements, false otherwise.
     */
    public static boolean isNameFormatValid(String name) {
        Pattern p = Pattern.compile("^[A-zА-я_]+$");
        Matcher m = p.matcher(name);
        return m.matches();
    }
}
