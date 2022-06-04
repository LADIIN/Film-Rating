package com.epam.filmrating.controller.command;

import com.epam.filmrating.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Interface for basic request and response processing.
 *
 * @author Vladislav Darkovich
 */
public interface Command {

    /**
     * This method is responsible for request and response processing.
     *
     * @param request
     * @param response
     * @return CommandResult
     * @throws ServiceException
     */
    CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
}
