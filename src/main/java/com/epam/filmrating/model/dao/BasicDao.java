package com.epam.filmrating.model.dao;

import com.epam.filmrating.exception.DaoException;
import com.epam.filmrating.model.entity.Identifiable;

import java.util.List;
import java.util.Optional;

/**
 * Basic DAO operations for Identifiable.
 *
 * @author Darkovich Vladislav
 */

public interface BasicDao<T extends Identifiable> {
    /**
     * Finding all Identifiable.
     * @return List of Identifiable
     * @throws DaoException
     */
    List<T> findAll() throws DaoException;

    /**
     * Find Identifiable by id.
     * @param id
     * @return Optional of Identifiable
     * @throws DaoException
     */
    Optional<T> findById(Long id) throws DaoException;

    /**
     * Updating Identifiable.
     * @param t
     * @return is updated
     * @throws DaoException
     */
    boolean update(T t) throws DaoException;

    /**
     * Deleting Identifiable by id.
     * @param id
     * @return is deleted.
     * @throws DaoException
     */
    boolean delete(Long id) throws DaoException;

    /**
     * Adding Identifiable.
     * @param t
     * @return is added.
     * @throws DaoException
     */
    boolean add(T t) throws DaoException;

    /**
     * Counting Identifiable.
     * @return amount of Identifiable
     * @throws DaoException
     */
    int countAll() throws DaoException;
}
