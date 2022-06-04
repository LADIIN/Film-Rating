package com.epam.filmrating.controller.command.impl.common;

import com.epam.filmrating.controller.command.Command;
import com.epam.filmrating.controller.command.CommandResult;
import com.epam.filmrating.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * class LocaleCommand
 *
 * @author Vladislav Darkovich
 */
public class LocaleCommand implements Command {
    public static final String CURRENT_PAGE = "current_page";
    public static final String SESSION_LOCALE = "locale";
    public static final String LOCALE = "newLocale";
    public static final String ENGLISH = "en_US";
    public static final String RUSSIAN = "ru_RU";
    public static final String BELARUSIAN = "by_BY";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);

        String locale = request.getParameter(LOCALE);
        if (locale.equals(ENGLISH) || locale.equals(RUSSIAN) || locale.equals(BELARUSIAN)) {
            session.setAttribute(SESSION_LOCALE, locale);
        }
        return CommandResult.forward(currentPage);
    }
}
