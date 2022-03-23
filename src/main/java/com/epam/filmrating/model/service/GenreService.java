package com.epam.filmrating.model.service;

import com.epam.filmrating.exception.DaoException;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.exception.TransactionException;
import com.epam.filmrating.model.dao.impl.GenreDaoImpl;
import com.epam.filmrating.model.entity.Genre;
import com.epam.filmrating.model.pool.TransactionManager;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class GenreService {
    private final TransactionManager transactionManager = TransactionManager.getInstance();

    public Optional<Genre> findById(Long id) throws ServiceException {
        Optional<Genre> genreOptional;

        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            GenreDaoImpl genreDao = new GenreDaoImpl(connection);
            genreOptional = genreDao.findById(id);
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return genreOptional;
    }

    public List<Genre> findAll() throws ServiceException {
        List<Genre> genres;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            GenreDaoImpl genreDao = new GenreDaoImpl(connection);
            genres = genreDao.findAll();
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return genres;
    }

    public Optional<Genre> findByFilmId(Long filmId) throws ServiceException {
        Optional<Genre> genreOptional;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            GenreDaoImpl genreDao = new GenreDaoImpl(connection);
            genreOptional = genreDao.findByFilmId(filmId);
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return genreOptional;
    }
}
