package com.epam.filmrating.model.dao.mapper.impl;

import com.epam.filmrating.model.dao.mapper.RowMapper;
import com.epam.filmrating.model.entity.Country;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryRowMapper implements RowMapper<Country> {
    @Override
    public Country resultToObject(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(Country.ID);
        String name = resultSet.getString(Country.NAME);
        return new Country(id, name);
    }
}
