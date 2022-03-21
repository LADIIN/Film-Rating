package com.epam.filmrating.model.dao.impl;

import com.epam.filmrating.exception.DaoException;
import com.epam.filmrating.model.dao.AbstractDao;
import com.epam.filmrating.model.dao.mapper.RowMapper;
import com.epam.filmrating.model.dao.mapper.impl.GenreRowMapper;
import com.epam.filmrating.model.entity.Genre;

import java.sql.Connection;
import java.util.Optional;

public class GenreDaoImpl extends AbstractDao<Genre> {
    private static final String TABLE_NAME = "genres";
    private static final String SELECT_GENRE_BY_FILM_ID = "SELECT genres.id, genres.genre FROM genres JOIN films on genres.id = genre_id where films.id = ?;";

    public GenreDaoImpl(Connection connection) {
        super(new GenreRowMapper(), connection, TABLE_NAME);
    }

    public Optional<Genre> findByFilmId(Long filmId) throws DaoException {
        return executeQueryForSingleObject(SELECT_GENRE_BY_FILM_ID, filmId);
    }

}
