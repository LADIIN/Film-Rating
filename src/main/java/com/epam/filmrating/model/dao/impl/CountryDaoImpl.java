package com.epam.filmrating.model.dao.impl;

import com.epam.filmrating.exception.DaoException;
import com.epam.filmrating.model.dao.AbstractDao;
import com.epam.filmrating.model.dao.mapper.impl.CountryRowMapper;
import com.epam.filmrating.model.entity.Country;

import java.sql.Connection;
import java.util.Optional;

/**
 * Dao for Country.
 *
 * @author Darkovich Vladislav.
 */
public class CountryDaoImpl extends AbstractDao<Country> {
    /**
     * Table name in database.
     */
    private static final String TABLE_NAME = "countries";
    /**
     * Query to select country by Film id.
     */
    private static final String SELECT_COUNTRY_BY_FILM_ID = "SELECT countries.id, countries.country FROM countries JOIN films on countries.id = country_id where films.id = ?;";

    /**
     * Constructor.
     *
     * @param connection
     */
    public CountryDaoImpl(Connection connection) {
        super(new CountryRowMapper(), connection, TABLE_NAME);
    }

    /**
     * Finding Country by Film id.
     *
     * @param filmId
     * @return Optional of Country
     * @throws DaoException
     */
    public Optional<Country> findByFilmId(Long filmId) throws DaoException {
        return executeSelectQueryForSingleObject(SELECT_COUNTRY_BY_FILM_ID, filmId);
    }
}
