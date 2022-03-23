package com.epam.filmrating.controller.command.impl.admin;

import com.epam.filmrating.controller.command.Command;
import com.epam.filmrating.controller.command.CommandResult;
import com.epam.filmrating.controller.command.Pages;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.model.entity.Country;
import com.epam.filmrating.model.entity.Film;
import com.epam.filmrating.model.entity.Genre;
import com.epam.filmrating.model.service.CountryService;
import com.epam.filmrating.model.service.FilmService;
import com.epam.filmrating.model.service.GenreService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.time.Year;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class ShowEditFilmPageCommand implements Command {
    public static final String CURRENT_PAGE = "current_page";
    private static final String ID = "id";
    private static final String FILM = "film";
    private static final String GENRES = "genres";
    private static final String COUNTRIES = "countries";
    private static final String CURRENT_YEAR = "currentYear";

    private final FilmService filmService;

    private final GenreService genreService;
    private final CountryService countryService;


    public ShowEditFilmPageCommand(FilmService filmService, GenreService genreService, CountryService countryService) {
        this.filmService = filmService;
        this.genreService = genreService;
        this.countryService = countryService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        List<Genre> genres = genreService.findAll();
        session.setAttribute(GENRES, genres);
        List<Country> countries = countryService.findAll();
        session.setAttribute(COUNTRIES, countries);
        Long id = Long.valueOf(request.getParameter(ID));
        Optional<Film> filmOptional = filmService.findById(id);
        Film film = filmOptional.orElseThrow(IllegalArgumentException::new);
        session.setAttribute(FILM, film);

        Year currentYear = getCurrentYear();
        session.setAttribute(CURRENT_YEAR, currentYear);

        session.setAttribute(CURRENT_PAGE, Pages.EDIT_FILM_PAGE_REDIRECT + id);
        return CommandResult.forward(Pages.EDIT_FILM_PAGE);
    }

    private Year getCurrentYear() {
        return Year.of(Calendar.getInstance().get(Calendar.YEAR));
    }
}
