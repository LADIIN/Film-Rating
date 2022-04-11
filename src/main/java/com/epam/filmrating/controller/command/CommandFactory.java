package com.epam.filmrating.controller.command;

import com.epam.filmrating.controller.command.impl.common.LoginCommand;
import com.epam.filmrating.controller.command.impl.common.ShowMainPageCommand;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.epam.filmrating.controller.command.Pages.MAIN_PAGE;

/**
 * Creates Command depending on CommandType.
 *
 * @author Vladislav Darkovich
 */
public class CommandFactory {
    /**
     * Creates Command depending on CommandType.
     *
     * @param commandName
     * @return Command as Optional
     * @throws ServiceException
     */
    public Optional<Command> create(String commandName) throws ServiceException {

        Optional<Command> command = Optional.empty();
        if (commandName != null && !commandName.isEmpty()) {
            try {
                CommandType commandType = CommandType.valueOf(commandName.toUpperCase());
                command = Optional.of(commandType.getCommand());
            } catch (IllegalArgumentException e) {
                throw new ServiceException(e.getMessage());
            }
        }
        return command;
    }
}
