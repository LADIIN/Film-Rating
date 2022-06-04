package com.epam.filmrating.model.dao.impl;

import com.epam.filmrating.exception.DaoException;
import com.epam.filmrating.model.dao.AbstractDao;
import com.epam.filmrating.model.dao.mapper.impl.FilmRowMapper;
import com.epam.filmrating.model.entity.Film;
import com.epam.filmrating.model.entity.FilmType;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * Film DAO.
 *
 * @author Darkovich Vladislav.
 */
public class FilmDaoImpl extends AbstractDao<Film> {
    /**
     * Table name in database;
     */
    private static final String TABLE_NAME = "films";
    /**
     * Query to select all films.
     */
    private static final String SELECT_ALL = "SELECT films.id, films.title, film_types.type, genres.id as genre_id," +
            "genres.genre, films.year, films.director, countries.id as country_id, countries.country, films.rating," +
            "films.is_deleted, films.poster_path\n" +
            "FROM films\n" +
            "JOIN genres ON genres.id = films.genre_id\n" +
            "JOIN countries ON countries.id = films.country_id\n" +
            "JOIN film_types ON film_types.id = films.type_id\n" +
            "WHERE films.is_deleted = false;";

    /**
     * Query to select film by id.
     */
    private static final String SELECT_BY_ID = "SELECT films.id, films.title, film_types.type, genres.id as genre_id," +
            "genres.genre, films.year, films.director, countries.id as country_id, countries.country, films.rating, " +
            "films.is_deleted, films.poster_path\n" +
            "FROM films\n" +
            "JOIN genres ON genres.id = films.genre_id\n" +
            "JOIN countries ON countries.id = films.country_id\n" +
            "JOIN film_types ON film_types.id = films.type_id\n" +
            "WHERE films.is_deleted = false AND films.id = ?";
    /**
     * Query to select films by type.
     */
    private static final String SELECT_BY_TYPE = "SELECT films.id, films.title, film_types.type, genres.id as genre_id," +
            "genres.genre, films.year, films.director, countries.id as country_id, countries.country, films.rating," +
            "films.is_deleted, films.poster_path\n" +
            "FROM films\n" +
            "JOIN genres ON genres.id = films.genre_id\n" +
            "JOIN countries ON countries.id = films.country_id\n" +
            "JOIN film_types ON film_types.id = films.type_id\n" +
            "WHERE films.is_deleted = false\n" +
            "AND (SELECT film_types.type FROM film_types WHERE id = type_id) = ?;";
    /**
     * Query to select group of films.
     */
    private static final String SELECT_GROUP = "SELECT films.id, films.title, film_types.type, genres.id as genre_id," +
            "genres.genre, films.year, films.director, countries.id as country_id, countries.country, films.rating," +
            "films.is_deleted, films.poster_path\n" +
            "FROM films\n" +
            "JOIN genres ON genres.id = films.genre_id\n" +
            "JOIN countries ON countries.id = films.country_id\n" +
            "JOIN film_types ON film_types.id = films.type_id\n" +
            "WHERE films.is_deleted = false LIMIT ?,?;";
    /**
     * Query to add film.
     */
    public static final String INSERT_FILM = "INSERT INTO films \n" +
            "(title, type_id, genre_id, year, director, country_id, poster_path)\n" +
            "VALUES (?, (SELECT id FROM film_types WHERE type=?), " +
            "(SELECT id FROM genres WHERE genre=?),?, ?, " +
            "(SELECT id FROM countries WHERE country=?), ?);";
    /**
     * Query to update film rating.
     */
    private static final String UPDATE_RATING = "UPDATE films set rating = ? WHERE is_deleted = false AND id = ?;";

    /**
     * Query to update film.
     */
    private static final String UPDATE_FILM = "UPDATE films set \n" +
            "title = ?,\n" +
            "type_id = (SELECT id FROM film_types WHERE type = ?),\n" +
            "genre_id = (SELECT id FROM genres WHERE genre = ?),\n" +
            "year = ?,\n" +
            "director = ?,\n" +
            "country_id = (SELECT id FROM countries WHERE country = ?),\n" +
            "poster_path = ?\n" +
            "WHERE id = ?;";
    /**
     * Query to find films by title.
     */
    private static final String SELECT_BY_TITLE = "SELECT films.id, films.title, film_types.type, genres.id as genre_id," +
            " genres.genre, films.year, films.director, countries.id as country_id, countries.country, films.rating, films.is_deleted, films.poster_path\n" +
            "FROM films\n" +
            "JOIN genres ON genres.id = films.genre_id\n" +
            "JOIN countries ON countries.id = films.country_id\n" +
            "JOIN film_types ON film_types.id = films.type_id\n" +
            "WHERE films.is_deleted = false AND films.title LIKE ?";
    /**
     * Query to select group of films by type.
     */
    private static final String SELECT_GROUP_BY_TYPE = "SELECT films.id, films.title, film_types.type, genres.id as genre_id," +
            " genres.genre, films.year, films.director, countries.id as country_id, countries.country, films.rating, films.is_deleted, films.poster_path\n" +
            "FROM films\n" +
            "JOIN genres ON genres.id = films.genre_id\n" +
            "JOIN countries ON countries.id = films.country_id\n" +
            "JOIN film_types ON film_types.id = films.type_id\n" +
            "WHERE films.is_deleted = false AND (SELECT film_types.type FROM film_types WHERE id = type_id) = ? LIMIT ?,?;";
    /**
     * Query to select top of films by rating.
     */
    public static final String SELECT_TOP_BY_RATING = "SELECT films.id, films.title, film_types.type, genres.id as genre_id," +
            " genres.genre, films.year, films.director,countries.id as country_id, countries.country, films.rating, films.is_deleted, films.poster_path\n" +
            "FROM films\n" +
            "JOIN genres ON genres.id = films.genre_id\n" +
            "JOIN countries ON countries.id = films.country_id\n" +
            "JOIN film_types ON film_types.id = films.type_id \n" +
            "WHERE films.is_deleted = false ORDER BY rating DESC, title LIMIT ?;";
    /**
     * Query to count films of type.
     */
    private static final String COUNT_OF_TYPE = "SELECT count(*) FROM films WHERE is_deleted = false AND (SELECT film_types.type FROM film_types WHERE id = type_id) = ?;";

    /**
     * Query to check is film with such title exist.
     */
    private static final String SELECT_EXISTS_FILM = "SELECT exists (SELECT * FROM films WHERE is_deleted = false AND title = ? AND id != ?);";

    /**
     * Constructor.
     *
     * @param connection
     */
    public FilmDaoImpl(Connection connection) {
        super(new FilmRowMapper(), connection, TABLE_NAME);
    }

    @Override
    public Optional<Film> findById(Long id) throws DaoException {
        return executeSelectQueryForSingleObject(SELECT_BY_ID, id);
    }

    @Override
    public List<Film> findAll() throws DaoException {
        return executeSelectQuery(SELECT_ALL);
    }

    @Override
    public boolean update(Film film) throws DaoException {
        return executeUpdateInsertQuery(UPDATE_FILM, film.getTitle(),
                film.getType().toString(),
                film.getGenre().getName(),
                film.getYear().getValue(),
                film.getDirector(),
                film.getCountry().getName(),
                film.getPosterPath(), film.getId());
    }

    @Override
    public boolean add(Film film) throws DaoException {
        return executeUpdateInsertQuery(INSERT_FILM, film.getTitle(),
                film.getType().toString(),
                film.getGenre().getName(),
                film.getYear().getValue(),
                film.getDirector(),
                film.getCountry().getName(),
                film.getPosterPath());
    }

    /**
     * Finding films by type.
     *
     * @param type
     * @return List of Films.
     * @throws DaoException
     */
    public List<Film> findByType(FilmType type) throws DaoException {
        return executeSelectQuery(SELECT_BY_TYPE, type.toString());
    }

    /**
     * Getting group of films.
     *
     * @param offset
     * @param rowcount
     * @return List of Films.
     * @throws DaoException
     */
    public List<Film> getGroup(int offset, int rowcount) throws DaoException {
        return executeSelectQuery(SELECT_GROUP, offset, rowcount);
    }

    /**
     * Updating film rating.
     *
     * @param id
     * @param rating
     * @return is updated.
     * @throws DaoException
     */
    public boolean updateRatingById(Long id, double rating) throws DaoException {
        return executeUpdateInsertQuery(UPDATE_RATING, rating, id);
    }

    /**
     * Search film by title.
     *
     * @param title
     * @return List of Films.
     * @throws DaoException
     */
    public List<Film> searchByTitle(String title) throws DaoException {
        return executeSelectQuery(SELECT_BY_TITLE, "%" + title + "%");
    }

    /**
     * Checking is film already exist.
     *
     * @param title
     * @param id
     * @return Number of films.
     * @throws DaoException
     */
    public Number isFilmAlreadyExists(String title, Long id) throws DaoException {
        return executeSelectFunctionQuery(SELECT_EXISTS_FILM, title, id);
    }

    /**
     * Getting group of films by type.
     * @param filmType
     * @param offset
     * @param rowcount
     * @return List of Films.
     * @throws DaoException
     */
    public List<Film> getGroupByType(FilmType filmType, int offset, int rowcount) throws DaoException {
        return executeSelectQuery(SELECT_GROUP_BY_TYPE, filmType.toString(), offset, rowcount);
    }

    /**
     * Counting films of type.
     * @param filmType
     * @return
     * @throws DaoException
     */
    public Number countOfType(FilmType filmType) throws DaoException {
        return executeSelectFunctionQuery(COUNT_OF_TYPE, filmType.toString());
    }

    /**
     * Getting films top by rating.
     * @param topSize
     * @return List of Films.
     * @throws DaoException
     */
    public List<Film> getTopByRating(int topSize) throws DaoException {
        return executeSelectQuery(SELECT_TOP_BY_RATING, topSize);
    }

}
