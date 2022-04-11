package com.epam.filmrating.controller.command;

import com.epam.filmrating.controller.command.impl.admin.*;
import com.epam.filmrating.controller.command.impl.common.*;
import com.epam.filmrating.model.service.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.epam.filmrating.controller.command.CommandAccessLevel.*;

/**
 * Enum of Command types with access levels.
 * @author Vladislav Darkovich.
 */

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
    DELETE_REVIEW(new DeleteReviewCommand(new ReviewService()), ADMIN),
    REGISTER_PAGE(new ShowRegisterPageCommand(), UNAUTHORIZED, ADMIN, USER),
    REGISTER(new RegisterCommand(new UserService()), UNAUTHORIZED, ADMIN, USER),
    RATING_PAGE(new ShowRatingPageCommand(new FilmService()), ADMIN, USER);

    /**
     * Command.
     */
    private final Command command;
    /**
     * Set of command access levels.
     */
    private final Set<CommandAccessLevel> accessLevels;


    /**
     * CommandType constructor.
     * @param command
     * @param accessLevels
     */
    CommandType(Command command, CommandAccessLevel... accessLevels) {
        this.command = command;
        this.accessLevels = new HashSet<>(Arrays.asList(accessLevels));
    }
    /**
     * Allows getting command;
     * @return Command
     */
    public Command getCommand() {
        return command;
    }
    /**
     * Allows checking is command accessible.
     * @param commandAccessLevel
     * @return true if command accessible and false otherwise.
     */
    public boolean isAccessible(CommandAccessLevel commandAccessLevel) {
        return accessLevels.stream().anyMatch(accessLevel -> accessLevel == commandAccessLevel);
    }
}
