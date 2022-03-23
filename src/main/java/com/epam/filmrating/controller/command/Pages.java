package com.epam.filmrating.controller.command;

public class Pages {
    public static final String LOGIN_PAGE = "/pages/login.jsp";
    public static final String MAIN_PAGE = "/pages/mainPage.jsp";
    public static final String USERS_PAGE = "/pages/users.jsp";
    public static final String FILM_PAGE = "/pages/film.jsp";
    public static final String FILMS_PAGE = "/pages/films.jsp";
    public static final String ADD_FILM_PAGE = "/pages/addFilm.jsp";
    public static final String EDIT_FILM_PAGE = "/pages/editFilm.jsp";
    public static final String SEARCH_FILM_RESULT_ADMIN = "/pages/searchFilmResult.jsp";
    public static final String SEARCH_USERS_RESULT_PAGE = "/pages/searchUsersResult.jsp";
    public static final String SEARCH_RESULT_MAIN_PAGE = "/pages/searchResultMainPage.jsp";
    public static final String REGISTER_PAGE = "/pages/register.jsp";


    public static final String MAIN_PAGE_REDIRECT = "/controller?command=main_page&film_type=";
    public static final String LOGIN_PAGE_REDIRECT = "/controller?command=login_page";
    public static final String USERS_PAGE_REDIRECT = "/controller?command=users_page&page=";
    public static final String FILM_PAGE_REDIRECT = "/controller?command=film_page&id=";
    public static final String FILMS_PAGE_REDIRECT = "/controller?command=films_page&page=";
    public static final String ADD_FILM_PAGE_REDIRECT = "/controller?command=add_film_page";
    public static final String EDIT_FILM_PAGE_REDIRECT = "/controller?command=edit_film_page&id=";
    public static final String REGISTER_PAGE_REDIRECT = "/controller?command=register_page";

}
