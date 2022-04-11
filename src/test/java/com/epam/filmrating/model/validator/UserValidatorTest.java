package com.epam.filmrating.model.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class UserValidatorTest {

    private static Stream<String> provideValidLogins() {
        return Stream.of(
                "login",
                "LOGIN",
                "login.regex",
                "login-regex",
                "login_regex",
                "login.123",
                "login-regex",
                "login_regex",
                "login123",
                "login name"
        );
    }

    @ParameterizedTest(name = "#{index} - Run test with login = {0}")
    @MethodSource("provideValidLogins")
    public void testIsLoginValidShouldReturnTrueWhenIsCorrect(String login) {
        //given
        UserValidator userValidator = new UserValidator();
        //when
        boolean actual = userValidator.isLoginValid(login);
        //then
        Assertions.assertTrue(actual);
    }

    public static Stream<String> provideInvalidLogins() {
        return Stream.of(
                "",
                "      ",
                "ab",
                null,
                "asdfghjklmnvcxsdsds",
                "123345",
                "_login_",
                ".login.",
                "-login-",
                "login#$%@123",
                "<script>alert(123)</script>"
        );
    }

    @ParameterizedTest(name = "#{index} - Run test with login = {0}")
    @MethodSource("provideInvalidLogins")
    public void testIsLoginValidShouldReturnFalseWhenIsIncorrect(String login) {
        //given
        UserValidator userValidator = new UserValidator();
        //when
        boolean actual = userValidator.isLoginValid(login);
        //then
        Assertions.assertFalse(actual);
    }

    public static Stream<String> provideValidPasswords() {
        return Stream.of(
                "AAAbbbccc23",
                "123sdsd232",
                "sds23dsd12"
        );
    }

    @ParameterizedTest(name = "#{index} - Run test with password = {0}")
    @MethodSource("provideValidPasswords")
    public void testIsPasswordValidShouldReturnTrueWhenIsCorrect(String password) {
        //given
        UserValidator userValidator = new UserValidator();
        //when
        boolean actual = userValidator.isPasswordValid(password);
        //then
        Assertions.assertTrue(actual);
    }

    public static Stream<String> provideInvalidPasswords() {
        return Stream.of(
                "12345678",
                "",
                " ",
                null,
                "a",
                "asdkdkfjdslfkjosifjsdokf",
                "sdsd sdsd12"
        );
    }

    @ParameterizedTest(name = "#{index} - Run test with password = {0}")
    @MethodSource("provideInvalidPasswords")
    public void testIsPasswordValidShouldReturnFalseWhenIsIncorrect(String password) {
        //given
        UserValidator userValidator = new UserValidator();
        //when
        boolean actual = userValidator.isPasswordValid(password);
        //then
        Assertions.assertFalse(actual);
    }

    public static Stream<String> provideValidEmails() {
        return Stream.of(
                "email@example.com",
                "email@example.co.by",
                "h@example.com"
        );
    }

    @ParameterizedTest(name = "#{index} - Run test with email = {0}")
    @MethodSource("provideValidEmails")
    public void testIsEmailValidShouldReturnTrueWhenIsCorrect(String email) {
        //given
        UserValidator userValidator = new UserValidator();
        //when
        boolean actual = userValidator.isEmailValid(email);
        //then
        Assertions.assertTrue(actual);
    }

    public static Stream<String> provideInvalidEmails() {
        return Stream.of(
                "",
                " ",
                null,
                "email",
                "@gmail.com",
                "email@.com",
                "email.com",
                "email@"
        );
    }

    @ParameterizedTest(name = "#{index} - Run test with email = {0}")
    @MethodSource("provideInvalidEmails")
    public void testIsEmailValidShouldReturnFalseWhenIsIncorrect(String email) {
        //given
        UserValidator userValidator = new UserValidator();
        //when
        boolean actual = userValidator.isEmailValid(email);
        //then
        Assertions.assertFalse(actual);
    }
}
