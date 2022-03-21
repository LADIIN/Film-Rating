package com.epam.filmrating.controller.filter;

import com.epam.filmrating.controller.command.Pages;
import com.epam.filmrating.model.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PageRedirectFilter implements Filter {
    private static final String USER = "user";

    private Set<String> unauthorizedPages;
    private Set<String> adminPages;
    private Set<String> userPages;
    private Set<String> pages;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        unauthorizedPages = new HashSet<>(Arrays.asList(Pages.LOGIN_PAGE));
        adminPages = new HashSet<>(Arrays.asList(
                Pages.LOGIN_PAGE,
                Pages.MAIN_PAGE,
                Pages.USERS_PAGE,
                Pages.FILM_PAGE,
                Pages.FILMS_PAGE,
                Pages.ADD_FILM_PAGE,
                Pages.EDIT_FILM_PAGE,
                Pages.SEARCH_FILM_RESULT_ADMIN,
                Pages.SEARCH_RESULT_MAIN_PAGE,
                Pages.SEARCH_USERS_RESULT_PAGE
        ));

        userPages = new HashSet<>(Arrays.asList(
                Pages.LOGIN_PAGE,
                Pages.MAIN_PAGE,
                Pages.FILM_PAGE,
                Pages.SEARCH_RESULT_MAIN_PAGE
        ));
        pages = new HashSet<>();
        pages.addAll(unauthorizedPages);
        pages.addAll(adminPages);
        pages.addAll(userPages);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getServletPath();
        boolean isPageExist = pages.stream().anyMatch(requestURI::contains);

        if (!isPageExist) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER);
            boolean isAccessible;

            if (user == null) {
                isAccessible = unauthorizedPages.stream().anyMatch(requestURI::contains);
            } else if (user.isAdmin()) {
                isAccessible = adminPages.stream().anyMatch(requestURI::contains);
            } else {
                isAccessible = userPages.stream().anyMatch(requestURI::contains);
            }

            if (!isAccessible) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

}
