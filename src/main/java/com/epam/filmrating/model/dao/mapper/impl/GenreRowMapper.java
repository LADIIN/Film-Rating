package com.epam.filmrating.model.dao.mapper.impl;

import com.epam.filmrating.model.dao.mapper.RowMapper;
import com.epam.filmrating.model.entity.Film;
import com.epam.filmrating.model.entity.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreRowMapper implements RowMapper<Genre> {
    @Override
    public Genre resultToObject(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(Film.FILM_ID);
        String name = resultSet.getString(Film.GENRE);
        return new Genre(id, name);
    }
}
