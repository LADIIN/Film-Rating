package com.epam.filmrating.controller.command;

import com.epam.filmrating.exception.CommandException;
import com.epam.filmrating.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Command {
    CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, CommandException;
}
