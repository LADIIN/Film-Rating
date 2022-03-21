package com.epam.filmrating.controller.command.impl.common;

import com.epam.filmrating.controller.command.Command;
import com.epam.filmrating.controller.command.CommandResult;
import com.epam.filmrating.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LocaleCommand implements Command {
    public static final String CURRENT_PAGE = "current_page";
    public static final String SESSION_LOCALE = "locale";
    public static final String LOCALE = "newLocale";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);

        String newLocal = request.getParameter(LOCALE);
        if (newLocal.equals("en_US") || newLocal.equals("ru_RU") || newLocal.equals("by_BY")) {
            session.setAttribute(SESSION_LOCALE, newLocal);
        }

        return CommandResult.forward(currentPage);
    }
}
