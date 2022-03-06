package com.epam.filmrating.controller.command;

import com.epam.filmrating.controller.command.impl.admin.*;
import com.epam.filmrating.controller.command.impl.common.AddReviewCommand;
import com.epam.filmrating.controller.command.impl.common.LocaleCommand;
import com.epam.filmrating.controller.command.impl.common.LoginCommand;
import com.epam.filmrating.controller.command.impl.common.LogoutCommand;
import com.epam.filmrating.controller.command.impl.user.*;
import com.epam.filmrating.model.service.FilmService;
import com.epam.filmrating.model.service.ReviewService;
import com.epam.filmrating.model.service.UserService;


public enum CommandType {
    LOGIN(new LoginCommand(new UserService())),
    LOCALE(new LocaleCommand()),
    LOGIN_PAGE(new ShowLoginPageCommand()),
    MAIN_PAGE(new ShowMainPageCommand(new FilmService())),
    LOG_OUT(new LogoutCommand()),
    USERS_PAGE(new ShowUsersPageCommand(new UserService())),
    FILM_PAGE(new ShowFilmPageCommand(new FilmService(), new ReviewService())),
    FILMS_PAGE(new ShowFilmsPageCommand(new FilmService())),
    BLOCK_USER(new BlockUserCommand(new UserService())),
    DELETE_FILM(new DeleteFilmCommand(new FilmService())),
    ADD_REVIEW(new AddReviewCommand(new ReviewService(), new FilmService(), new UserService())),
    ADD_FILM_PAGE(new ShowAddFilmPageCommand()),
    ADD_FILM(new AddFilmCommand(new FilmService())),
    EDIT_FILM_PAGE(new ShowEditFilmPageCommand(new FilmService())),
    EDIT_FILM(new EditFilmCommand(new FilmService()));

    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
