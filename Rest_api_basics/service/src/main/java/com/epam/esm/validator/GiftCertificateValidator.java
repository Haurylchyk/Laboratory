package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDTO;

import java.util.List;

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
     * The maximum permitted length of the description.
     */
    private static final int MAX_VALID_DESC_LENGTH = 255;

    /**
     * Private constructor.
     */
    private GiftCertificateValidator() {
    }

    /**
     * Checks that the data is valid.
     *
     * @param giftCertificateDTO GiftCertificate entity.
     * @return true if the data is valid, false otherwise.
     */
    public static boolean isValidDataForCreate(GiftCertificateDTO giftCertificateDTO) {
        return isNameValid(giftCertificateDTO.getName())
                && isDescriptionValid(giftCertificateDTO.getDescription())
                && isPriceValid(giftCertificateDTO.getPrice())
                && isDurationValid(giftCertificateDTO.getDuration());
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
        return !CommonValidator.isEmpty(name)
                && CommonValidator.isStringLengthValid(name);
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

    /**
     * Checks that the data is valid.
     *
     * @param giftCertificateDTO GiftCertificate entity.
     * @return true if the data is valid, false otherwise.
     */
    public static boolean isValidDataForUpdate(GiftCertificateDTO giftCertificateDTO) {
        String name = giftCertificateDTO.getName();
        if (name != null && !isNameValid(giftCertificateDTO.getName())) {
            return false;
        }

        String description = giftCertificateDTO.getDescription();
        if (description != null && !isDescriptionValid(giftCertificateDTO.getDescription())) {
            return false;
        }

        Integer price = giftCertificateDTO.getPrice();
        if (price != null && !isPriceValid(giftCertificateDTO.getPrice())) {
            return false;
        }

        Integer duration = giftCertificateDTO.getDuration();
        if (duration != null && !isDurationValid(giftCertificateDTO.getDuration())) {
            return false;
        }
        return validateTagList(giftCertificateDTO.getTagNames());
    }

    private static boolean validateTagList(List<String> tagNamesList) {
        if (tagNamesList == null) {
            return true;
        }
        if (tagNamesList.isEmpty()) {
            return false;
        }
        for (String tagName : tagNamesList) {
            if (!TagValidator.isNameValid(tagName)) {
                return false;
            }
        }
        return true;
    }
}
