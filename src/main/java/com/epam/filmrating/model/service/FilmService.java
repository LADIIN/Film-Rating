package com.epam.filmrating.model.service;

import com.epam.filmrating.exception.DaoException;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.exception.TransactionException;
import com.epam.filmrating.model.dao.impl.FilmDaoImpl;
import com.epam.filmrating.model.entity.*;
import com.epam.filmrating.model.pool.TransactionManager;
import com.epam.filmrating.model.validator.FilmValidator;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.time.Year;
import java.util.List;
import java.util.Optional;

/**
 * Provides access to {@link FilmDaoImpl} and operations with {@link Film}.
 */
public class FilmService {
    /**
     * Transaction manager.
     */
    private final TransactionManager transactionManager = TransactionManager.getInstance();
    /**
     * Directory path where all film poster will be saved.
     */
    private static final String POSTERS_DIRECTORY =
            "C:\\Users\\ACER\\IdeaProjects\\Java Development Course\\Film-Rating\\src\\main\\webapp\\assets\\posters\\";
    private static final int MAX_SIZE_IMAGE = 1024 * 1024 * 10;

    /**
     * Finds all films.
     *
     * @return {@link List} of {@link Film}.
     * @throws ServiceException
     */
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
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return films;
    }

    /**
     * Finds film by Id.
     *
     * @param id
     * @return {@link Optional} of {@link Film}
     * @throws ServiceException
     */
    public Optional<Film> findById(Long id) throws ServiceException {
        Optional<Film> filmOptional;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            FilmDaoImpl filmDao = new FilmDaoImpl(connection);
            filmOptional = filmDao.findById(id);
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return filmOptional;
    }


    /**
     * Finds films for page by {@link FilmType}.
     *
     * @param type
     * @param currentPage
     * @param filmsOnPage
     * @return {@link List} of {@link Film}
     * @throws ServiceException
     */
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
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return movies;
    }

    /**
     * Deletes {@link Film} by ID.
     *
     * @param id
     * @return true if is deleted and false otherwise.
     * @throws ServiceException
     */
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
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return isDeleted;
    }

    /**
     * Finds group of films for page.
     *
     * @param currentPage
     * @param filmsOnPage
     * @return {@link List} of {@link Film}
     * @throws ServiceException
     */
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
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return films;
    }

    /**
     * Returns amount of films
     *
     * @return int
     * @throws ServiceException
     */
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
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return amount;
    }

    /**
     * Returns amount of films of {@link FilmType}.
     *
     * @param filmType
     * @return int
     * @throws ServiceException
     */
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
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return amount;
    }

    /**
     * Adds {@link Film}.
     *
     * @param title
     * @param type
     * @param genre
     * @param year
     * @param director
     * @param country
     * @param posterName
     * @param inputStream
     * @return true if is added and false otherwise.
     * @throws ServiceException
     */
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

    /**
     * Uploads image file.
     *
     * @param inputStream
     * @param filePath
     * @throws IOException
     */
    private void uploadFile(InputStream inputStream, String filePath) throws IOException {
        File file = new File(filePath);
        FileUtils.copyInputStreamToFile(inputStream, file);
    }

    /**
     * Edits {@link Film}.
     *
     * @param id
     * @param title
     * @param type
     * @param genre
     * @param year
     * @param director
     * @param country
     * @param posterName
     * @param inputStream
     * @return true if is edited and false otherwise.
     * @throws ServiceException
     */
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

    /**
     * Updates rating.
     *
     * @param filmId
     * @return true if is updated and false otherwise.
     * @throws ServiceException
     */
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

    /**
     * Searches films by title.
     *
     * @param title
     * @return {@link List} of {@link Film}
     * @throws ServiceException
     */
    public List<Film> searchByTitle(String title) throws ServiceException {
        List<Film> films;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            FilmDaoImpl filmDao = new FilmDaoImpl(connection);
            films = filmDao.searchByTitle(title);
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return films;
    }

    /**
     * Returns top of films by rating.
     *
     * @param ratingSize
     * @return {@link List} of {@link Film}
     * @throws ServiceException
     */
    public List<Film> createRating(int ratingSize) throws ServiceException {
        List<Film> films;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            FilmDaoImpl filmDao = new FilmDaoImpl(connection);
            films = filmDao.getTopByRating(ratingSize);
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return films;
    }

}
