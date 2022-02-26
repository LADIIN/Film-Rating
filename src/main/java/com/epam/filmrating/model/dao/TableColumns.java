package com.epam.filmrating.model.dao;

public final class TableColumns {
    /**
     * Users table
     */
    public static final String TABLE_USERS = "users";

    /**
     * User columns
     */
    public static final String USER_ID = "id";
    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD = "password";
    public static final String USER_EMAIL = "email";
    public static final String USER_STATUS = "status";
    public static final String USER_IS_ADMIN = "is_admin";
    public static final String USER_IS_BLOCKED = "is_blocked";

    /**
     * Film columns
     */
    public static final String FILM_ID = "id";
    public static final String TYPE = "type";
    public static final String TITLE = "title";
    public static final String GENRE = "genre";
    public static final String YEAR = "year";
    public static final String DIRECTOR = "director";
    public static final String COUNTRY = "country";
    public static final String RATING = "rating";
    public static final String POSTER_PATH = "poster_path";
}
