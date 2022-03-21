package com.epam.filmrating.controller.command;

import com.epam.filmrating.controller.command.impl.admin.*;
import com.epam.filmrating.controller.command.impl.common.AddReviewCommand;
import com.epam.filmrating.controller.command.impl.common.LocaleCommand;
import com.epam.filmrating.controller.command.impl.common.LoginCommand;
import com.epam.filmrating.controller.command.impl.common.LogoutCommand;
import com.epam.filmrating.controller.command.impl.user.*;
import com.epam.filmrating.model.service.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.epam.filmrating.controller.command.CommandAccessLevel.*;

public enum CommandType {
    LOGIN(new LoginCommand(new UserService()), UNAUTHORIZED, ADMIN, USER),
    LOCALE(new LocaleCommand(), UNAUTHORIZED, ADMIN, USER),
    LOGIN_PAGE(new ShowLoginPageCommand(), UNAUTHORIZED, ADMIN, USER),
    MAIN_PAGE(new ShowMainPageCommand(new FilmService()), ADMIN, USER),
    LOG_OUT(new LogoutCommand(), ADMIN, USER),
    USERS_PAGE(new ShowUsersPageCommand(new UserService()), ADMIN),
    FILM_PAGE(new ShowFilmPageCommand(new FilmService(), new ReviewService()), ADMIN, USER),
    FILMS_PAGE(new ShowFilmsPageCommand(new FilmService()), ADMIN),
    BLOCK_USER(new BlockUserCommand(new UserService()), ADMIN),
    DELETE_FILM(new DeleteFilmCommand(new FilmService()), ADMIN),
    ADD_REVIEW(new AddReviewCommand(new ReviewService(), new FilmService(), new UserService()), ADMIN, USER),
    ADD_FILM_PAGE(new ShowAddFilmPageCommand(new GenreService(), new CountryService()), ADMIN),
    ADD_FILM(new AddFilmCommand(new FilmService()), ADMIN),
    EDIT_FILM_PAGE(new ShowEditFilmPageCommand(new FilmService(), new GenreService(), new CountryService()), ADMIN),
    EDIT_FILM(new EditFilmCommand(new FilmService()), ADMIN),
    SEARCH_FILM(new SearchFilmCommand(new FilmService()), ADMIN, USER),
    CHANGE_STATUS(new ChangeStatusCommand(new UserService()), ADMIN),
    CHANGE_ROLE(new ChangeUserRoleCommand(new UserService()), ADMIN),
    SEARCH_USER(new SearchUserCommand(new UserService()), ADMIN),
    DELETE_REVIEW(new DeleteReviewCommand(new ReviewService()), ADMIN);

    private final Command command;
    private Set<CommandAccessLevel> accessLevels;

    CommandType(Command command, CommandAccessLevel... accessLevels) {
        this.command = command;
        this.accessLevels = new HashSet<>(Arrays.asList(accessLevels));
    }

    public Command getCommand() {
        return command;
    }

    public boolean isAccessible(CommandAccessLevel commandAccessLevel) {
        return accessLevels.stream().anyMatch(accessLevel -> accessLevel == commandAccessLevel);
    }
}
