package com.epam.filmrating.controller;

import com.epam.filmrating.controller.command.*;
import com.epam.filmrating.exception.CommandException;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.model.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ControllerServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    public static final String COMMAND = "command";
    public static final String ERROR = "errorMessage";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandName = req.getParameter(COMMAND);
        LOGGER.info("Command: {}", commandName);
        CommandFactory commandFactory = new CommandFactory();
        Command command = commandFactory.create(commandName).orElseThrow(IllegalArgumentException::new);

        try {
            CommandResult result = command.execute(req, resp);
            dispatch(req, resp, result);
        } catch (ServiceException | CommandException e) {
            req.setAttribute(ERROR, e.getMessage());
            dispatch(req, resp, CommandResult.forward("/pages/error.jsp"));
        }
    }

    private void dispatch(HttpServletRequest req, HttpServletResponse resp, CommandResult result) throws ServletException, IOException {
        String page = result.getPage();
        if (!result.isRedirect()) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + page);
        }
    }
}
