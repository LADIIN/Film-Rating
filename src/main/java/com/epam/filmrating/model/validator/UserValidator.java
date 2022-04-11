package com.epam.filmrating.model.validator;

import com.epam.filmrating.model.entity.User;

/**
 * Validates {@link User}.
 */
public class UserValidator {
    /**
     * Regex of {@link User} login.
     */
    private static final String LOGIN_REGEX = "^[a-zA-Z][a-zA-Z0-9-_. ]{3,16}$";
    /**
     * Regex of {@link User} password.
     */
    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*\\p{Lower})[\\d\\p{Alpha}]{8,16}$";
    /**
     * Regex of {@link User} email.
     */
    private static final String EMAIL_REGEX = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";

    /**
     * @param user
     * @param password
     * @return true if {@link User} is valid and false otherwise.
     */
    public boolean isValid(User user, String password) {
        return isLoginValid(user.getLogin()) && isPasswordValid(password) && isEmailValid(user.getEmail());
    }

    /**
     *
     * @param login
     * @return true if login is valid and false otherwise.
     */
    public boolean isLoginValid(String login) {
        return login != null && login.matches(LOGIN_REGEX);
    }

    /**
     * @param password
     * @return true if password is valid and false otherwise.
     */
    public boolean isPasswordValid(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }

    /**
     *
     * @param email
     * @return true if email is valid and false otherwise.
     */
    public boolean isEmailValid(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

}
