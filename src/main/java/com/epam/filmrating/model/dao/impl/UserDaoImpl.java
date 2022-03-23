package com.epam.filmrating.model.dao.impl;

import com.epam.filmrating.exception.DaoException;
import com.epam.filmrating.model.dao.AbstractDao;
import com.epam.filmrating.model.dao.mapper.impl.UserRowMapper;
import com.epam.filmrating.model.entity.User;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;


public class UserDaoImpl extends AbstractDao<User> {
    private static final String TABLE_NAME = "users";
    private static final String FIND_BY_LOGIN_AND_PASSWORD = "SELECT * FROM users WHERE login = ? and password = ?";
    private static final String UPDATE_USER = "UPDATE users SET status = ?, is_admin = ?, is_blocked = ? where id = ?;";
    private static final String SELECT_GROUP = "select * from users limit ?,?;";
    private static final String COUNT_USERS = "select count(*) from users;";
    private static final String RAISE_STATUS = "update users set status = status + ? where is_blocked = false and id = ?;";
    private static final String UPDATE_STATUS = "update users set status = ? where id = ?;";
    private static final String SELECT_BY_LOGIN = "select * from users where login like ?;";
    private static final String ADD_USER = "insert users (login, password, email) values (?, ?, ?);";
    private static final String COUNT_USER_BY_LOGIN_AND_EMAIL = "select count(*) from users where login = ? or email = ?; ";

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

    public boolean add(User user, String password) throws DaoException {
        return executeUpdateInsertQuery(ADD_USER, user.getLogin(), password, user.getEmail());
    }

    @Override
    public int countAll() throws DaoException {
        return executeSelectFunctionQuery(COUNT_USERS).intValue();
    }

    public List<User> getGroup(int offset, int rowcount) throws DaoException {
        return executeSelectQuery(SELECT_GROUP, offset, rowcount);
    }

    public void raiseStatus(Long id, int status) throws DaoException {
        executeUpdateInsertQuery(RAISE_STATUS, status, id);
    }

    public boolean updateStatus(Long id, int status) throws DaoException {
        return executeUpdateInsertQuery(UPDATE_STATUS, status, id);
    }

    public List<User> searchByLogin(String login) throws DaoException {
        return executeSelectQuery(SELECT_BY_LOGIN, "%" + login + "%");
    }

    public int countUsersWithLoginAndEmail(String login, String email) throws DaoException {
        return executeSelectFunctionQuery(COUNT_USER_BY_LOGIN_AND_EMAIL, login, email).intValue();
    }

}
