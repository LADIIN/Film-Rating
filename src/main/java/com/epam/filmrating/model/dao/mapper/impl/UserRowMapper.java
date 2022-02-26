package com.epam.filmrating.model.dao.mapper.impl;

import com.epam.filmrating.model.dao.mapper.RowMapper;
import com.epam.filmrating.model.entity.Identifiable;
import com.epam.filmrating.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.filmrating.model.dao.TableColumns.*;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User resultToObject(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(USER_ID);
        String login = resultSet.getString(USER_LOGIN);
        String email = resultSet.getString(USER_EMAIL);
        int status = resultSet.getInt(USER_STATUS);
        boolean isAdmin = resultSet.getBoolean(USER_IS_ADMIN);
        boolean isBlocked = resultSet.getBoolean(USER_IS_BLOCKED);

        return new User.Builder().setId(id)
                .setLogin(login)
                .setEmail(email)
                .setStatus(status)
                .setAdmin(isAdmin)
                .setBlocked(isBlocked)
                .build();
    }
}
