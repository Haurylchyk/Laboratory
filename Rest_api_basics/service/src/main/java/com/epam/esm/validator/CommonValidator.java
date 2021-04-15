package com.epam.esm.validator;

import java.util.Optional;

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
     * The maximum permitted length of the name.
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
        return false;
    }

    /**
     * Checks if the string meets the requirements.
     *
     * @param str string for verification.
     * @return true if the string meets the requirements, false otherwise.
     */
    public static boolean isNameLengthValid(String str) {
        return str.length() <= MAX_VALID_NAME_LENGTH;
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
}
