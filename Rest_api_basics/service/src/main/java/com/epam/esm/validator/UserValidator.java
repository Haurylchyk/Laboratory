package com.epam.esm.validator;

import com.epam.esm.dto.UserDTO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class contains methods for checking field values
 * which describe the details of the User.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.0
 */
public class UserValidator {

    /**
     * The valid login format.
     */
    private static final String LOGIN_FORMAT_REGEX = "^[A-z0-9_]+$";


    /**
     * Checks that the data is valid.
     *
     * @param userDTO User entity.
     * @return true if the data is valid, false otherwise.
     */
    public static boolean isValidData(UserDTO userDTO) {
        return isNameValid(userDTO.getName())
                && isLoginValid(userDTO.getLogin());
    }

    /**
     * Checks that the name is not empty and doesn't
     * exceed the allowed length.
     *
     * @param name Tag name.
     * @return true if the name is not empty and doesn't
     * exceed the allowed length, false otherwise.
     */
    public static boolean isNameValid(String name) {
        return !CommonValidator.isEmpty(name)
                && CommonValidator.isStringLengthValid(name)
                && CommonValidator.isNameFormatValid(name);
    }

    /**
     * Checks if the login meets the requirements.
     *
     * @param login login for verification.
     * @return true if the login meets the requirements, false otherwise.
     */
    public static boolean isLoginValid(String login) {
        return !CommonValidator.isEmpty(login)
                && CommonValidator.isStringLengthValid(login)
                && isLoginFormatValid(login);
    }

    /**
     * Checks whether the login matches the specified format.
     *
     * @param login login for verification.
     * @return true if the login matches the format, false otherwise.
     */
    public static boolean isLoginFormatValid(String login) {
        Pattern p = Pattern.compile(LOGIN_FORMAT_REGEX);
        Matcher m = p.matcher(login);
        return m.matches();
    }
}
