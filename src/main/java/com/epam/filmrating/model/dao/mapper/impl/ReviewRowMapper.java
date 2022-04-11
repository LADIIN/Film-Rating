package com.epam.filmrating.model.dao.mapper.impl;

import com.epam.filmrating.model.dao.mapper.RowMapper;
import com.epam.filmrating.model.entity.Review;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewRowMapper implements RowMapper<Review> {

    @Override
    public Review resultToObject(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(Review.ID);
        int rate = resultSet.getInt(Review.RATE);
        String content = resultSet.getString(Review.CONTENT);
        Long userId = resultSet.getLong(Review.USER_ID);
        return new Review(id, rate, content, userId);
    }
}
