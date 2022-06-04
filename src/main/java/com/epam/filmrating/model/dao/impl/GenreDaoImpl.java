package com.epam.filmrating.model.dao.impl;

import com.epam.filmrating.exception.DaoException;
import com.epam.filmrating.model.dao.AbstractDao;
import com.epam.filmrating.model.dao.mapper.impl.GenreRowMapper;
import com.epam.filmrating.model.entity.Genre;

import java.sql.Connection;
import java.util.Optional;

/**
 * Genre dao.
 * @author Darkovich Vladislav.
 */
public class GenreDaoImpl extends AbstractDao<Genre> {
    /**
     * Table name in database.
     */
    private static final String TABLE_NAME = "genres";
    /**
     * Query to select genre by film id.
     */
    private static final String SELECT_GENRE_BY_FILM_ID = "SELECT genres.id, genres.genre FROM genres JOIN films on genres.id = genre_id WHERE films.id = ?;";

    /**
     * Constructor.
     * @param connection
     */
    public GenreDaoImpl(Connection connection) {
        super(new GenreRowMapper(), connection, TABLE_NAME);
    }

    /**
     * Finding Genre by Film id.
     * @param filmId
     * @return Optional of genre.
     * @throws DaoException
     */
    public Optional<Genre> findByFilmId(Long filmId) throws DaoException {
        return executeSelectQueryForSingleObject(SELECT_GENRE_BY_FILM_ID, filmId);
    }

}
