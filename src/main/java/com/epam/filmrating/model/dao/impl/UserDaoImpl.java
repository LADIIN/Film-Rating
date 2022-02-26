package com.epam.filmrating.model.dao.impl;

import com.epam.filmrating.exception.DaoException;
import com.epam.filmrating.model.dao.AbstractDao;
import com.epam.filmrating.model.dao.mapper.impl.UserRowMapper;
import com.epam.filmrating.model.entity.User;

import java.sql.Connection;
import java.util.Optional;


public class UserDaoImpl extends AbstractDao<User> {
    private static final String FIND_BY_LOGIN_AND_PASSWORD = "SELECT * FROM users WHERE login = ? and password = ?";
    private static final String TABLE_NAME = "users";

    public UserDaoImpl(Connection connection) {
        super(new UserRowMapper(), connection, TABLE_NAME);
    }

    public Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException {
        return executeQueryForSingleObject(FIND_BY_LOGIN_AND_PASSWORD, login, password);
    }

}
