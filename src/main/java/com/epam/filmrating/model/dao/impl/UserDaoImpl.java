package com.epam.filmrating.model.dao.impl;

import com.epam.filmrating.exception.DaoException;
import com.epam.filmrating.model.dao.AbstractDao;
import com.epam.filmrating.model.dao.mapper.impl.UserRowMapper;
import com.epam.filmrating.model.entity.User;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * User Dao.
 *
 * @author Darkovich Vladislav.
 */
public class UserDaoImpl extends AbstractDao<User> {
    /**
     * Table name in database.
     */
    private static final String TABLE_NAME = "users";
    /**
     * Query to select by login and password.
     */
    private static final String SELECT_BY_LOGIN_AND_PASSWORD = "SELECT * FROM users WHERE login = ? AND password = sha2(?,224)";
    /**
     * Query to update user data.
     */
    private static final String UPDATE_USER = "UPDATE users SET status = ?, is_admin = ?, is_blocked = ? WHERE id = ?;";
    /**
     * Query to select group of users.
     */
    private static final String SELECT_GROUP = "SELECT * FROM users limit ?,?;";
    /**
     * Query to count users.
     */
    private static final String COUNT_USERS = "SELECT count(*) FROM users;";
    /**
     * Query to raise user status.
     */
    private static final String RAISE_STATUS = "UPDATE users SET status = status + ? WHERE is_blocked = false AND id = ?;";
    /**
     * Query to update user status.
     */
    private static final String UPDATE_STATUS = "UPDATE users SET status = ? WHERE id = ?;";
    /**
     * Query to select user by login.
     */
    private static final String SELECT_BY_LOGIN = "SELECT * FROM users WHERE login LIKE ?;";
    /**
     * Query to add user.
     */
    private static final String ADD_USER = "INSERT users (login, password, email) values (?, sha2(?,224), ?);";
    /**
     * Query to count users by login or email.
     */
    private static final String COUNT_USER_BY_LOGIN_AND_EMAIL = "SELECT count(*) FROM users WHERE login = ? OR email = ?; ";

    /**
     * Constructor.
     *
     * @param connection
     */
    public UserDaoImpl(Connection connection) {
        super(new UserRowMapper(), connection, TABLE_NAME);
    }

    @Override
    public boolean update(User user) throws DaoException {
        return executeUpdateInsertQuery(UPDATE_USER, user.getStatus(), user.isAdmin(), user.isBlocked(), user.getId());
    }

    @Override
    public int countAll() throws DaoException {
        return executeSelectFunctionQuery(COUNT_USERS).intValue();
    }

    /**
     * Finding user by login and password.
     *
     * @param login
     * @param password
     * @return Optional of User
     * @throws DaoException
     */
    public Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException {
        return executeSelectQueryForSingleObject(SELECT_BY_LOGIN_AND_PASSWORD, login, password);
    }

    /**
     * Adding user.
     *
     * @param user
     * @param password
     * @return is added.
     * @throws DaoException
     */
    public boolean add(User user, String password) throws DaoException {
        return executeUpdateInsertQuery(ADD_USER, user.getLogin(), password, user.getEmail());
    }

    /**
     * Getting group of users.
     *
     * @param offset
     * @param rowcount
     * @return List of Users
     * @throws DaoException
     */
    public List<User> getGroup(int offset, int rowcount) throws DaoException {
        return executeSelectQuery(SELECT_GROUP, offset, rowcount);
    }

    /**
     * Raising user status.
     *
     * @param id
     * @param status
     * @throws DaoException
     */
    public void raiseStatus(Long id, int status) throws DaoException {
        executeUpdateInsertQuery(RAISE_STATUS, status, id);
    }

    /**
     * Updating user status.
     *
     * @param id
     * @param status
     * @return is updated.
     * @throws DaoException
     */
    public boolean updateStatus(Long id, int status) throws DaoException {
        return executeUpdateInsertQuery(UPDATE_STATUS, status, id);
    }

    /**
     * Searching users by login.
     *
     * @param login
     * @return List of users.
     * @throws DaoException
     */
    public List<User> searchByLogin(String login) throws DaoException {
        return executeSelectQuery(SELECT_BY_LOGIN, "%" + login + "%");
    }

    /**
     * Counting users with such login or email.
     *
     * @param login
     * @param email
     * @return amount of users.
     * @throws DaoException
     */
    public int countUsersWithLoginOrEmail(String login, String email) throws DaoException {
        return executeSelectFunctionQuery(COUNT_USER_BY_LOGIN_AND_EMAIL, login, email).intValue();
    }

}
