package com.epam.filmrating.controller.command;

import com.epam.filmrating.controller.command.impl.common.LoginCommand;
import com.epam.filmrating.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class CommandFactory {
    private static final Logger LOGGER = LogManager.getLogger();

    public Optional<Command> create(String commandName) {

        Optional<Command> command = Optional.empty();
        if (commandName != null && !commandName.isEmpty()) {
            try {
                CommandType commandType = CommandType.valueOf(commandName.toUpperCase());
                command = Optional.of(commandType.getCommand());
            } catch (IllegalArgumentException e) {
                LOGGER.error("Can't find command: {}.",commandName);
            }
        }
        return command;
    }
}
