package com.epam.filmrating.controller.filter;

import com.epam.filmrating.controller.command.CommandAccessLevel;
import com.epam.filmrating.controller.command.CommandType;
import com.epam.filmrating.model.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;

public class CommandAccessFilter implements Filter {
    private static final String USER = "user";
    private static final String COMMAND = "command";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpRequest.getSession();
        User user = (User) session.getAttribute(USER);
        CommandAccessLevel commandAccessLevel;

        if (user == null) {
            commandAccessLevel = CommandAccessLevel.UNAUTHORIZED;
        } else if (user.isAdmin()) {
            commandAccessLevel = CommandAccessLevel.ADMIN;
        } else {
            commandAccessLevel = CommandAccessLevel.USER;
        }
        String commandName = httpRequest.getParameter(COMMAND);
        if (isCommandValid(commandName)) {
            CommandType commandType = CommandType.valueOf(commandName.toUpperCase());
            if (commandType.isAccessible(commandAccessLevel)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                httpResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            httpResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private boolean isCommandValid(String commandName) {
        return commandName != null && Arrays.stream(CommandType.values())
                .anyMatch(commandType -> commandType.name().equalsIgnoreCase(commandName));
    }
}
