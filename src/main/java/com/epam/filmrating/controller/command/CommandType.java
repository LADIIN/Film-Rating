package com.epam.filmrating.controller.command;

import com.epam.filmrating.controller.command.impl.common.LocaleCommand;
import com.epam.filmrating.controller.command.impl.common.LoginCommand;
import com.epam.filmrating.controller.command.impl.common.LogoutCommand;
import com.epam.filmrating.controller.command.impl.user.ShowLoginPageCommand;
import com.epam.filmrating.controller.command.impl.user.ShowMainPageCommand;
import com.epam.filmrating.model.service.FilmService;
import com.epam.filmrating.model.service.UserService;

public enum CommandType {
    LOGIN(new LoginCommand(new UserService())),
    LOCALE(new LocaleCommand()),
    LOGIN_PAGE(new ShowLoginPageCommand()),
    MAIN_PAGE(new ShowMainPageCommand(new FilmService())),
    LOG_OUT(new LogoutCommand());

    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
