package com.epam.esm.validator;

/**
 * Class contains methods for checking field values
 * which describe the details of the GiftCertificate.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.0
 */
public class GiftCertificateValidator {

    /**
     * Private constructor.
     */
    private GiftCertificateValidator() {
    }

    /**
     * Checks that the name is not empty and doesn't
     * exceed the allowed length.
     *
     * @param name GiftCertificate name.
     * @return true if the name is not empty and doesn't
     * exceed the allowed length, false otherwise.
     */
    public static boolean isNameValid(String name) {
        return !CommonValidator.isEmpty(name) && CommonValidator.isNameLengthValid(name);
    }

    /**
     * Checks that the description is not empty and doesn't
     * exceed the allowed length.
     *
     * @param description GiftCertificate description.
     * @return true if the description is not empty and doesn't
     * exceed the allowed length, false otherwise.
     */
    public static boolean isDescriptionValid(String description) {
        final int MAX_VALID_DESC_LENGTH = 255;
        return !CommonValidator.isEmpty(description) && description.length() <= MAX_VALID_DESC_LENGTH;
    }

    /**
     * Checks that the price is not empty and is a positive number.
     *
     * @param price GiftCertificate price.
     * @return true if the description is not empty and is
     * a positive number, false otherwise.
     */
    public static boolean isPriceValid(Integer price) {
        return !CommonValidator.isEmpty(price) && CommonValidator.isPositiveNumber(price);
    }

    /**
     * Checks that the price is not empty and is a positive number.
     *
     * @param duration GiftCertificate duration.
     * @return true if the duration is not empty and is
     * a positive number, false otherwise.
     */
    public static boolean isDurationValid(Integer duration) {
        return !CommonValidator.isEmpty(duration) && CommonValidator.isPositiveNumber(duration);
    }
}
