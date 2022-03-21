package com.epam.filmrating.model.service;

import com.epam.filmrating.exception.DaoException;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.exception.TransactionException;
import com.epam.filmrating.model.dao.impl.CountryDaoImpl;
import com.epam.filmrating.model.dao.impl.GenreDaoImpl;
import com.epam.filmrating.model.entity.Country;
import com.epam.filmrating.model.entity.Genre;
import com.epam.filmrating.model.pool.TransactionManager;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class CountryService {
    private final TransactionManager transactionManager = TransactionManager.getInstance();

    public Optional<Country> findByFilmId(Long filmId) throws ServiceException {
        Optional<Country> countryOptional;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            CountryDaoImpl countryDao = new CountryDaoImpl(connection);
            countryOptional = countryDao.findByFilmId(filmId);
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return countryOptional;
    }

    public List<Country> findAll() throws ServiceException {
        List<Country> countries;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            CountryDaoImpl countryDao = new CountryDaoImpl(connection);
            countries = countryDao.findAll();
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return countries;
    }
}
