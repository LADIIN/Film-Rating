package com.epam.filmrating.model.dao.mapper;

import com.epam.filmrating.model.entity.Identifiable;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Basic RowMapper operations for Identifiable.
 * @param <T>
 * @author Darkovich Vladislav
 */
public interface RowMapper<T extends Identifiable> {
    /**
     * Converting ResultSet to Identifiable.
     * @param resultSet
     * @return Identifiable.
     * @throws SQLException
     */
    T resultToObject(ResultSet resultSet) throws SQLException;
}
