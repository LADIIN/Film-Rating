package com.epam.filmrating.controller;

import com.epam.filmrating.controller.command.*;
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

/**
 * Overrides doPost and doGet methods by calling custom method processRequest to upload data.
 *
 * @author Vladislav Darkovich.
 */
public class UploaderServlet extends HttpServlet {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    private static final String IMAGE = "image";
    private static final String IMAGE_INPUT_STREAM = "image_input_stream";
    private static final String IMAGE_NAME = "image_name";
    public static final String COMMAND = "command";
    public static final String ERROR = "errorMessage";

    /**
     * Called by the server to allow a servlet to handle a GET request.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Called by the server to allow a servlet to handle a POST upload data request.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part part = request.getPart(IMAGE);
        InputStream imageInputStream = part.getInputStream();
        String imageName = part.getSubmittedFileName();
        request.setAttribute(IMAGE_INPUT_STREAM, imageInputStream);
        request.setAttribute(IMAGE_NAME, imageName);
        processRequest(request, response);
    }

    /**
     * Process HttpServletRequest.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(COMMAND);
        LOGGER.info("Command: {}", commandName);
        CommandFactory commandFactory = new CommandFactory();

        try {
            Command command = commandFactory.create(commandName).orElseThrow(IllegalArgumentException::new);
            CommandResult result = command.execute(request, response);
            dispatch(request, response, result);
        } catch (ServiceException e) {
            request.setAttribute(ERROR, e.getMessage());
            dispatch(request, response, CommandResult.forward("/pages/error.jsp"));
        }
    }

    /**
     * Dispatches redirect or forward response corresponding CommandResult.
     *
     * @param request
     * @param response
     * @param result
     * @throws ServletException
     * @throws IOException
     */
    private void dispatch(HttpServletRequest request, HttpServletResponse response, CommandResult result) throws ServletException, IOException {
        String page = result.getPage();
        if (!result.isRedirect()) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + page);
        }
    }
}

