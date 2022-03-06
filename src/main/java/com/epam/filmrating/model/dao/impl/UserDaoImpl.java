package com.epam.filmrating.model.dao.impl;

import com.epam.filmrating.exception.DaoException;
import com.epam.filmrating.model.dao.AbstractDao;
import com.epam.filmrating.model.dao.mapper.impl.UserRowMapper;
import com.epam.filmrating.model.entity.User;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;


public class UserDaoImpl extends AbstractDao<User> {
    private static final String FIND_BY_LOGIN_AND_PASSWORD = "SELECT * FROM users WHERE login = ? and password = ?";
    private static final String TABLE_NAME = "users";
    private static final String UPDATE_USER = "UPDATE users SET status = ?, is_admin = ?, is_blocked = ? where id = ?;";
    private static final String SELECT_GROUP = "select * from users limit ?,?;";
    private static final String COUNT_USERS = "select count(*) from users;";
    private static final String UPDATE_STATUS = "update users set status = status + ? where is_blocked = false and id = ?";

    public UserDaoImpl(Connection connection) {
        super(new UserRowMapper(), connection, TABLE_NAME);
    }

    public Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException {
        return executeQueryForSingleObject(FIND_BY_LOGIN_AND_PASSWORD, login, password);
    }

    @Override
    public boolean update(User user) throws DaoException {
        return executeUpdateInsertQuery(UPDATE_USER, user.getStatus(), user.isAdmin(), user.isBlocked(), user.getId());
    }

    @Override
    public int countAll() throws DaoException {
        return executeSelectFunctionQuery(COUNT_USERS);
    }

    public List<User> getGroup(int offset, int rowcount) throws DaoException {
        return executeSelectQuery(SELECT_GROUP, offset, rowcount);
    }

    public boolean updateStatus(Long id, int status) throws DaoException {
        return executeUpdateInsertQuery(UPDATE_STATUS,status,id);
    }

}
