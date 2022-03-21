package com.epam.filmrating.model.service;

import com.epam.filmrating.exception.DaoException;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.exception.TransactionException;
import com.epam.filmrating.model.dao.impl.FilmDaoImpl;
import com.epam.filmrating.model.entity.*;
import com.epam.filmrating.model.pool.TransactionManager;
import com.epam.filmrating.model.validator.FilmValidator;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.time.Year;
import java.util.List;
import java.util.Optional;

public class FilmService {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    private final TransactionManager transactionManager = TransactionManager.getInstance();
    private static final String POSTERS_DIRECTORY =
            "C:\\Users\\ACER\\IdeaProjects\\Java Development Course\\Film-Rating\\src\\main\\webapp\\assets\\posters\\";
    private static final int MAX_SIZE_IMAGE = 1024 * 1024 * 10;


    public List<Film> findAllFilms() throws ServiceException {
        List<Film> films;
        GenreService genreService = new GenreService();

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
        Optional<Film> filmOptional;
        GenreService genreService = new GenreService();

        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            FilmDaoImpl filmDao = new FilmDaoImpl(connection);
            filmOptional = filmDao.findById(id);

            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            LOGGER.error("Finding movies error caused by ", e);
            throw new ServiceException("Finding movies error caused by ", e);
        } finally {
            transactionManager.endTransaction();
        }
        return filmOptional;

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

    public List<Film> findByTypeForPage(FilmType type, int currentPage, int filmsOnPage) throws ServiceException {
        List<Film> movies;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            FilmDaoImpl filmDao = new FilmDaoImpl(connection);
            int offset = (currentPage - 1) * filmsOnPage;
            movies = filmDao.getGroupByType(type, offset, filmsOnPage);
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

    public int countFilmsOfType(FilmType filmType) throws ServiceException {
        int amount = 0;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            FilmDaoImpl filmDao = new FilmDaoImpl(connection);
            amount = filmDao.countOfType(filmType).intValue();
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
                           Country country, String posterName, InputStream inputStream) throws ServiceException {
        boolean isAdded = false;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            FilmDaoImpl filmDao = new FilmDaoImpl(connection);
            FilmValidator filmValidator = new FilmValidator();

            String posterPath = POSTERS_DIRECTORY + posterName;
            Film film = new Film.Builder()
                    .setTitle(title)
                    .setType(type)
                    .setGenre(genre)
                    .setYear(year)
                    .setDirector(director)
                    .setCountry(country)
                    .setPosterPath(posterPath).build();
            if (filmValidator.isValid(film)) {
                filmDao.add(film);
                uploadFile(inputStream, posterPath);
                isAdded = true;
            }
            transactionManager.commit();
        } catch (TransactionException | DaoException | IOException e) {
            transactionManager.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return isAdded;
    }

    public void uploadFile(InputStream inputStream, String filePath) throws IOException {
        File file = new File(filePath);
        FileUtils.copyInputStreamToFile(inputStream, file);
    }

    //TODO:FIX FILE UPLOAD
    public boolean editFilm(Long id, String title, FilmType type, Genre genre, Year year, String director,
                            Country country, String posterName, InputStream inputStream) throws ServiceException {
        boolean isEdited = false;
        FilmValidator filmValidator = new FilmValidator();
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            FilmDaoImpl filmDao = new FilmDaoImpl(connection);
            Optional<Film> filmOptional = filmDao.findById(id);

            if (filmOptional.isPresent()) {
                Film film = filmOptional.get();
                film.setTitle(title);
                film.setType(type);
                film.setGenre(genre);
                film.setYear(year);
                film.setDirector(director);
                film.setCountry(country);

                if (filmValidator.isValid(film)) {
                    if (posterName != null && !posterName.isEmpty() && inputStream != null) {
                        String posterPath = POSTERS_DIRECTORY + posterName;
                        film.setPosterPath(posterPath);
                        uploadFile(inputStream, posterPath);
                    }
                    filmDao.update(film);
                    isEdited = true;
                }
            }
            transactionManager.commit();
        } catch (TransactionException | DaoException | IOException e) {
            transactionManager.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return isEdited;
    }

    public boolean updateRating(Long filmId) throws ServiceException {
        ReviewService reviewService = new ReviewService();
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            double rating = reviewService.getAverageFilmRate(filmId);
            FilmDaoImpl filmDao = new FilmDaoImpl(connection);
            filmDao.updateRatingById(filmId, rating);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return true;
    }

    public List<Film> searchByTitle(String title) throws ServiceException {
        List<Film> movies;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            FilmDaoImpl filmDao = new FilmDaoImpl(connection);
            movies = filmDao.searchByTitle(title);
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

    public boolean isFilmAlreadyExist(String title, Long id) throws ServiceException {
        int amount = 0;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            FilmDaoImpl filmDao = new FilmDaoImpl(connection);
            amount = filmDao.isFilmAlreadyExists(title, id).intValue();
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return amount != 0;
    }

    public static void main(String[] args) throws ServiceException {
        FilmService filmService = new FilmService();
        filmService.findById(1l).ifPresent(System.out::println);

    }
}
