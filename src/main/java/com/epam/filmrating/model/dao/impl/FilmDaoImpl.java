package com.epam.filmrating.model.dao.impl;

import com.epam.filmrating.exception.DaoException;
import com.epam.filmrating.model.dao.AbstractDao;
import com.epam.filmrating.model.dao.mapper.impl.FilmRowMapper;
import com.epam.filmrating.model.entity.Film;
import com.epam.filmrating.model.entity.FilmType;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class FilmDaoImpl extends AbstractDao<Film> {
    private static final String TABLE_NAME = "films";
    private static final String SELECT_ALL = "select films.id, films.title, film_types.type, genres.id as genre_id," +
            "genres.genre, films.year, films.director, countries.id as country_id, countries.country, films.rating, films.is_deleted, films.poster_path\n" +
            "from films\n" +
            "join genres on genres.id = films.genre_id\n" +
            "join countries on countries.id = films.country_id\n" +
            "join film_types on film_types.id = films.type_id\n" +
            "where films.is_deleted = false;";

    private static final String SELECT_BY_ID = "select films.id, films.title, film_types.type, genres.id as genre_id," +
            " genres.genre, films.year, films.director, countries.id as country_id, countries.country, films.rating, films.is_deleted, films.poster_path\n" +
            "from films\n" +
            "join genres on genres.id = films.genre_id\n" +
            "join countries on countries.id = films.country_id\n" +
            "join film_types on film_types.id = films.type_id\n" +
            "where films.is_deleted = false and films.id = ?";

    private static final String SELECT_BY_TYPE = "select films.id, films.title, film_types.type, genres.id as genre_id," +
            " genres.genre, films.year, films.director, countries.id as country_id, countries.country, films.rating, films.is_deleted, films.poster_path\n" +
            "from films\n" +
            "join genres on genres.id = films.genre_id\n" +
            "join countries on countries.id = films.country_id\n" +
            "join film_types on film_types.id = films.type_id\n" +
            "where films.is_deleted = false\n" +
            "and (select film_types.type from film_types where id = type_id) = ?;";

    private static final String SELECT_GROUP = "select films.id, films.title, film_types.type, genres.id as genre_id," +
            " genres.genre, films.year, films.director, countries.id as country_id, countries.country, films.rating, films.is_deleted, films.poster_path\n" +
            "from films\n" +
            "join genres on genres.id = films.genre_id\n" +
            "join countries on countries.id = films.country_id\n" +
            "join film_types on film_types.id = films.type_id\n" +
            "where films.is_deleted = false limit ?,?;";

    public static final String INSERT_FILM = "INSERT INTO films \n" +
            "(title, type_id, genre_id, year, director, country_id, poster_path)\n" +
            "VALUES (?, (select id from film_types where type=?), " +
            "(select id from genres where genre=?),?, ?, " +
            "(select id from countries where country=?), ?);";

    private static final String UPDATE_RATING = "update films set rating = ? where is_deleted = false and id = ?;";

    private static final String UPDATE_FILM = "update films set \n" +
            "title = ?,\n" +
            "type_id = (select id from film_types where type = ?),\n" +
            "genre_id = (select id from genres where genre = ?),\n" +
            "year = ?,\n" +
            "director = ?,\n" +
            "country_id = (select id from countries where country = ?),\n" +
            "poster_path = ?\n" +
            "where id = ?;";

    private static final String SELECT_BY_TITLE = "select films.id, films.title, film_types.type, genres.id as genre_id," +
            " genres.genre, films.year, films.director, countries.id as country_id, countries.country, films.rating, films.is_deleted, films.poster_path\n" +
            "from films\n" +
            "join genres on genres.id = films.genre_id\n" +
            "join countries on countries.id = films.country_id\n" +
            "join film_types on film_types.id = films.type_id\n" +
            "where films.is_deleted = false and films.title like ?";

    private static final String SELECT_GROUP_BY_TYPE = "select films.id, films.title, film_types.type, genres.id as genre_id," +
            " genres.genre, films.year, films.director, countries.id as country_id, countries.country, films.rating, films.is_deleted, films.poster_path\n" +
            "from films\n" +
            "join genres on genres.id = films.genre_id\n" +
            "join countries on countries.id = films.country_id\n" +
            "join film_types on film_types.id = films.type_id\n" +
            "where films.is_deleted = false and (select film_types.type from film_types where id = type_id) = ? limit ?,?;";

    private static final String COUNT_OF_TYPE = "select count(*) from films where is_deleted = false and (select film_types.type from film_types where id = type_id) = ?;";

    private static final String SELECT_EXISTS_FILM = "select exists (select * from films where is_deleted = false and title = ? and id != ?);";

    public FilmDaoImpl(Connection connection) {
        super(new FilmRowMapper(), connection, TABLE_NAME);
    }

    @Override
    public Optional<Film> findById(Long id) throws DaoException {
        return executeQueryForSingleObject(SELECT_BY_ID, id);
    }

    @Override
    public List<Film> findAll() throws DaoException {
        return executeSelectQuery(SELECT_ALL);
    }

    public List<Film> findByType(FilmType type) throws DaoException {
        return executeSelectQuery(SELECT_BY_TYPE, type.toString());
    }

    public List<Film> getGroup(int offset, int rowcount) throws DaoException {
        return executeSelectQuery(SELECT_GROUP, offset, rowcount);
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

    public boolean updateRatingById(Long id, double rating) throws DaoException {
        return executeUpdateInsertQuery(UPDATE_RATING, rating, id);
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

    public List<Film> searchByTitle(String title) throws DaoException {
        return executeSelectQuery(SELECT_BY_TITLE, "%" + title + "%");
    }

    public Number isFilmAlreadyExists(String title, Long id) throws DaoException {
        return executeSelectFunctionQuery(SELECT_EXISTS_FILM, title, id);
    }

    public List<Film> getGroupByType(FilmType filmType, int offset, int rowcount) throws DaoException {
        return executeSelectQuery(SELECT_GROUP_BY_TYPE, filmType.toString(), offset, rowcount);
    }

    public Number countOfType(FilmType filmType) throws DaoException {
        return executeSelectFunctionQuery(COUNT_OF_TYPE,filmType.toString());
    }

}
