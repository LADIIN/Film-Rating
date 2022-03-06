package com.epam.filmrating.model.dao.mapper.impl;

import com.epam.filmrating.model.dao.mapper.RowMapper;
import com.epam.filmrating.model.entity.Review;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewRowMapper implements RowMapper<Review> {
    private static final String ID = "id";
    private static final String RATE = "rate";
    private static final String CONTENT = "content";
    private static final String USER_ID = "user_id";

    @Override
    public Review resultToObject(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(ID);
        int rate = resultSet.getInt(RATE);
        String content = resultSet.getString(CONTENT);
        Long userId = resultSet.getLong(USER_ID);
        return new Review(id, rate, content, userId);
    }
}
