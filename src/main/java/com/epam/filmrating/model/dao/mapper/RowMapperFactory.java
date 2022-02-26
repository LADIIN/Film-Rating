package com.epam.filmrating.model.dao.mapper;

import com.epam.filmrating.model.dao.mapper.RowMapper;
import com.epam.filmrating.model.dao.mapper.impl.FilmRowMapper;
import com.epam.filmrating.model.dao.mapper.impl.UserRowMapper;
import com.epam.filmrating.model.entity.Identifiable;

public class RowMapperFactory<T extends Identifiable> {

    public static RowMapper<? extends Identifiable> create(String tableName) {
        switch (tableName) {
            case "USER":
                return new UserRowMapper();
            case "FIlM":
                return new FilmRowMapper();
            default:
                throw new IllegalArgumentException("Got unknown table:" + tableName);
        }
    }
}
