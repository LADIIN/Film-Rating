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

/**
 * Provides access to {@link CountryDaoImpl} and operations with {@link Country}.
 */
public class CountryService {
    /**
     * Transaction manager.
     */
    private final TransactionManager transactionManager = TransactionManager.getInstance();

    /**
     * Finds all countries.
     *
     * @return {@link List} of {@link Country}
     * @throws ServiceException
     */
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
