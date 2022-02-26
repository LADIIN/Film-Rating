package com.epam.filmrating.model.dao.impl;

import com.epam.filmrating.exception.DaoException;
import com.epam.filmrating.exception.TransactionException;
import com.epam.filmrating.model.dao.AbstractDao;
import com.epam.filmrating.model.dao.mapper.impl.FilmRowMapper;
import com.epam.filmrating.model.entity.Film;
import com.epam.filmrating.model.entity.FilmType;
import com.epam.filmrating.model.entity.Genre;
import com.epam.filmrating.model.pool.TransactionManager;

import java.sql.Connection;
import java.util.List;

public class FilmDaoImpl extends AbstractDao<Film> {
    private static final String TABLE_NAME = "films";
    private static final String SELECT_ALL = "select films.id, films.title, film_types.type, genres.genre, films.year, films.director, countries.country, films.rating, films.is_deleted, films.poster_path\n" +
            "from films\n" +
            "join genres on genres.id = films.genre_id\n" +
            "join countries on countries.id = films.country_id\n" +
            "join film_types on film_types.id = films.type_id\n" +
            "where films.is_deleted = false;";

    private static final String SELECT_BY_GENRE = "select films.id, films.title, film_types.type, genres.genre, films.year, films.director, countries.country, films.rating, films.is_deleted, films.poster_path\n" +
            "from films\n" +
            "join genres on genres.id = films.genre_id\n" +
            "join countries on countries.id = films.country_id\n" +
            "join film_types on film_types.id = films.type_id\n" +
            "where films.is_deleted = false\n" +
            "and (select film_types.type from film_types where id = type_id) = ?;";
    public FilmDaoImpl(Connection connection) {
        super(new FilmRowMapper(), connection, TABLE_NAME);
    }

    @Override
    public List<Film> findAll() throws DaoException {
        return executeSelectQuery(SELECT_ALL);
    }

    public List<Film> findByType(FilmType type) throws DaoException {
        return executeSelectQuery(SELECT_BY_GENRE, type.toString());
    }


    public static void main(String[] args) throws TransactionException, DaoException {
        TransactionManager transactionManager = TransactionManager.getInstance();
        transactionManager.initializeTransaction();

        Connection connection = transactionManager.getConnection();

        FilmDaoImpl filmDao = new FilmDaoImpl(connection);

        List<Film> films = filmDao.findByType(FilmType.Movie);
        films.forEach(System.out::println);
    }

}
