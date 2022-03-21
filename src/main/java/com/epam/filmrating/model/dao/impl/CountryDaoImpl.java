package com.epam.filmrating.model.dao.impl;

import com.epam.filmrating.exception.DaoException;
import com.epam.filmrating.model.dao.AbstractDao;
import com.epam.filmrating.model.dao.mapper.impl.CountryRowMapper;
import com.epam.filmrating.model.entity.Country;

import java.sql.Connection;
import java.util.Optional;

public class CountryDaoImpl extends AbstractDao<Country> {
    private static final String TABLE_NAME = "countries";
    private static final String SELECT_FILM_BY_FILM_ID = "SELECT countries.id, countries.country FROM countries JOIN films on countries.id = country_id where films.id = ?;";

    public CountryDaoImpl(Connection connection) {
        super(new CountryRowMapper(), connection, TABLE_NAME);
    }

    public Optional<Country> findByFilmId(Long filmId) throws DaoException {
        return executeQueryForSingleObject(SELECT_FILM_BY_FILM_ID, filmId);
    }
}
