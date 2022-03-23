package com.epam.filmrating.model.validator;

import com.epam.filmrating.model.entity.User;

public class UserValidator {
    private static final String LOGIN_REGEX = "^[a-zA-Z][a-zA-Z0-9-_]{3,16}$";
    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*\\p{Lower})[\\d\\p{Alpha}]{8,16}$";
    private static final String EMAIL_REGEX = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";

    public boolean isValid(User user, String password) {

        return isLoginValid(user.getLogin()) && isPasswordValid(password) && isEmailValid(user.getEmail());
    }

    private boolean isLoginValid(String login) {
        return login != null && login.matches(LOGIN_REGEX);
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }

    private boolean isEmailValid(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

}
