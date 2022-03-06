package com.epam.filmrating.model.service;

import com.epam.filmrating.exception.DaoException;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.exception.TransactionException;
import com.epam.filmrating.model.dao.impl.FilmDaoImpl;
import com.epam.filmrating.model.entity.Country;
import com.epam.filmrating.model.entity.Film;
import com.epam.filmrating.model.entity.FilmType;
import com.epam.filmrating.model.entity.Genre;
import com.epam.filmrating.model.pool.TransactionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.time.Year;
import java.util.List;
import java.util.Optional;

public class FilmService {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    private final TransactionManager transactionManager = TransactionManager.getInstance();
    private static final String POSTER_DIRECTORY =
            "C:\\Users\\ACER\\IdeaProjects\\Java Development Course\\Film-Rating\\src\\main\\webapp\\assets\\posters\\";


    public List<Film> findAllFilms() throws ServiceException {
        List<Film> films;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            FilmDaoImpl filmDao = new FilmDaoImpl(connection);
            films = filmDao.findAll();
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            LOGGER.error("Finding all films error caused by ", e);
            throw new ServiceException("Finding all films error caused by ", e);
        } finally {
            transactionManager.endTransaction();
        }
        return films;
    }

    public Optional<Film> findById(Long id) throws ServiceException {
        Optional<Film> film;

        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            FilmDaoImpl filmDao = new FilmDaoImpl(connection);
            film = filmDao.findById(id);
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            LOGGER.error("Finding movies error caused by ", e);
            throw new ServiceException("Finding movies error caused by ", e);
        } finally {
            transactionManager.endTransaction();
        }
        return film;

    }

    public List<Film> findByType(FilmType type) throws ServiceException {
        List<Film> movies;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            FilmDaoImpl filmDao = new FilmDaoImpl(connection);
            movies = filmDao.findByType(type);
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            LOGGER.error("Finding movies error caused by ", e);
            throw new ServiceException("Finding movies error caused by ", e);
        } finally {
            transactionManager.endTransaction();
        }
        return movies;
    }

    public boolean deleteFilm(Long id) throws ServiceException {
        boolean isDeleted = false;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            FilmDaoImpl filmDao = new FilmDaoImpl(connection);
            isDeleted = filmDao.delete(id);
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            LOGGER.error("Finding movies error caused by ", e);
            throw new ServiceException("Finding movies error caused by ", e);
        } finally {
            transactionManager.endTransaction();
        }
        return isDeleted;
    }

    public List<Film> findGroupForPage(int currentPage, int filmsOnPage) throws ServiceException {
        List<Film> films;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            FilmDaoImpl filmDao = new FilmDaoImpl(connection);
            int offset = (currentPage - 1) * filmsOnPage;
            films = filmDao.getGroup(offset, filmsOnPage);
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            LOGGER.error("Finding group of films error caused by ", e);
            throw new ServiceException("Finding group of films error caused by ", e);
        } finally {
            transactionManager.endTransaction();
        }
        return films;
    }

    public int countFilms() throws ServiceException {
        int amount = 0;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            FilmDaoImpl filmDao = new FilmDaoImpl(connection);
            amount = filmDao.countAll();
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            LOGGER.error("Counting all films error caused by ", e);
            throw new ServiceException("Counting all films error caused by ", e);
        } finally {
            transactionManager.endTransaction();
        }
        return amount;
    }

    public boolean addFilm(String title, FilmType type, Genre genre, Year year, String director,
                           Country country, String posterName) throws ServiceException {
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            FilmDaoImpl filmDao = new FilmDaoImpl(connection);

            String posterPath = POSTER_DIRECTORY + posterName;
            Film film = new Film.Builder()
                    .setTitle(title)
                    .setType(type)
                    .setGenre(genre)
                    .setYear(year)
                    .setDirector(director)
                    .setCountry(country)
                    .setPosterPath(posterPath).build();

            filmDao.add(film);
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return true;
    }

    public boolean editFilm(Long id, String title, FilmType type, Genre genre, Year year, String director,
                            Country country, String posterName) throws ServiceException {
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            FilmDaoImpl filmDao = new FilmDaoImpl(connection);

            String posterPath = posterName.contains(POSTER_DIRECTORY) ? posterName
                    : POSTER_DIRECTORY + posterName;

            System.out.println(posterPath);

            Film film = new Film.Builder()
                    .setId(id)
                    .setTitle(title)
                    .setType(type)
                    .setGenre(genre)
                    .setYear(year)
                    .setDirector(director)
                    .setCountry(country)
                    .setPosterPath(posterPath).build();

            filmDao.update(film);
            System.out.println(film);
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return true;
    }

    public boolean updateRating(Long filmId) throws ServiceException {
        ReviewService reviewService = new ReviewService();
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            double rating = reviewService.calculateRating(filmId);
            FilmDaoImpl filmDao = new FilmDaoImpl(connection);
            filmDao.updateRatingById(filmId, rating);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return true;
    }


    public static void main(String[] args) throws ServiceException {
        FilmService filmService = new FilmService();
        filmService.updateRating(1L);
    }
}
