package com.epam.filmrating.model.dao.mapper.impl;

import com.epam.filmrating.model.dao.mapper.RowMapper;
import com.epam.filmrating.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRowMapper implements RowMapper<User> {
    @Override
    public User resultToObject(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(User.ID);
        String login = resultSet.getString(User.LOGIN);
        String email = resultSet.getString(User.EMAIL);
        int status = resultSet.getInt(User.STATUS);
        boolean isAdmin = resultSet.getBoolean(User.IS_ADMIN);
        boolean isBlocked = resultSet.getBoolean(User.IS_BLOCKED);

        return new User.Builder().setId(id)
                .setLogin(login)
                .setEmail(email)
                .setStatus(status)
                .setAdmin(isAdmin)
                .setBlocked(isBlocked)
                .build();
    }
}
