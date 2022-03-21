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
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

public class UploaderServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    private static final String IMAGE = "image";
    private static final String IMAGE_INPUT_STREAM = "image_input_stream";
    private static final String IMAGE_NAME = "image_name";
    public static final String COMMAND = "command";
    public static final String ERROR = "errorMessage";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Part part = request.getPart(IMAGE);
        InputStream imageInputStream = part.getInputStream();
        String imageName = part.getSubmittedFileName();
        request.setAttribute(IMAGE_INPUT_STREAM, imageInputStream);
        request.setAttribute(IMAGE_NAME, imageName);

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

