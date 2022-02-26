package com.epam.filmrating.model.dao.mapper;

import com.epam.filmrating.model.entity.Identifiable;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T extends Identifiable> {
    T resultToObject(ResultSet resultSet) throws SQLException;
}
