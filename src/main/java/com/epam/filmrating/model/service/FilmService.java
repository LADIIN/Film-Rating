package com.epam.filmrating.model.service;

import com.epam.filmrating.exception.DaoException;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.exception.TransactionException;
import com.epam.filmrating.model.dao.impl.FilmDaoImpl;
import com.epam.filmrating.model.entity.Film;
import com.epam.filmrating.model.entity.FilmType;
import com.epam.filmrating.model.pool.TransactionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.List;

public class FilmService {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    private final TransactionManager transactionManager = TransactionManager.getInstance();

    public List<Film> findAllFilms() throws ServiceException {
        List<Film> films;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            FilmDaoImpl filmDao = new FilmDaoImpl(connection);
            films = filmDao.findAll();
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            LOGGER.error("Finding all films error caused by ", e);
            throw new ServiceException("Finding all films error caused by ", e);
        } finally {
            transactionManager.endTransaction();
        }
        return films;
    }

    public List<Film> findByType(FilmType type) throws ServiceException {
        List<Film> movies;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            FilmDaoImpl filmDao = new FilmDaoImpl(connection);
            movies = filmDao.findByType(type);
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            LOGGER.error("Finding movies error caused by ", e);
            throw new ServiceException("Finding movies error caused by ", e);
        } finally {
            transactionManager.endTransaction();
        }
        return movies;
    }


}
