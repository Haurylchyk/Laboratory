package com.epam.esm.validator;

/**
 * Class contains methods for checking field values
 * which describe the details of the Tag.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.0
 */
public class TagValidator {

    /**
     * Private constructor.
     */
    private TagValidator() {
    }

    /**
     * Checks that the name is not empty and doesn't
     * exceed the allowed length.
     *
     * @param name Tag name.
     * @return true if the name is not empty and doesn't
     * exceed the allowed length, false otherwise.
     */
    public static boolean checkName(String name) {
        return !CommonValidator.isEmpty(name) && CommonValidator.checkNameLength(name);
    }
}
