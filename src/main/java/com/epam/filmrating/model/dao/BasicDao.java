package com.epam.filmrating.model.dao;

import com.epam.filmrating.exception.DaoException;
import com.epam.filmrating.model.entity.Identifiable;

import java.util.List;
import java.util.Optional;

public interface BasicDao<T extends Identifiable> {
    List<T> findAll() throws DaoException;

    Optional<T> findById(Long id) throws DaoException;

    boolean update(T t) throws DaoException;

    boolean delete(Long id) throws DaoException;

    boolean add(T t) throws DaoException;

    int countAll() throws DaoException;
}
