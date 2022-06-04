package com.epam.filmrating.model.dao;

import com.epam.filmrating.exception.DaoException;
import com.epam.filmrating.model.dao.mapper.RowMapper;
import com.epam.filmrating.model.entity.Identifiable;

import java.sql.*;
import java.util.*;

/**
 * Default DAO for Identifiable.
 *
 * @author Darkovich Vladislav
 */
public abstract class AbstractDao<T extends Identifiable> implements BasicDao<T> {
    /**
     * Mapper for converting database query result to Identifiable.
     */
    private final RowMapper<T> rowMapper;
    /**
     * Database connection.
     */
    private final Connection connection;
    /**
     * Database table name.
     */
    private final String tableName;

    /**
     * Constructor.
     *
     * @param rowMapper
     * @param connection
     * @param tableName
     */
    public AbstractDao(RowMapper<T> rowMapper, Connection connection, String tableName) {
        this.rowMapper = rowMapper;
        this.connection = connection;
        this.tableName = tableName;
    }

    /**
     * Executing SELECT query in database.
     *
     * @param query
     * @param parameters
     * @return List of Identifiable
     * @throws DaoException
     */
    public List<T> executeSelectQuery(String query, Object... parameters) throws DaoException {
        List<T> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            setParametersInPreparedStatement(preparedStatement, parameters);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T object = rowMapper.resultToObject(resultSet);
                list.add(object);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return list;
    }

    /**
     * Executing SELECT query in database for single Identifiable.
     * @param query
     * @param parameters
     * @return Optional of Identifiable
     * @throws DaoException
     */
    public Optional<T> executeSelectQueryForSingleObject(String query, Object... parameters) throws DaoException {
        Optional<T> result = Optional.empty();
        List<T> list = executeSelectQuery(query, parameters);
        if (!list.isEmpty()) {
            result = Optional.of(list.get(0));
        }
        return result;
    }

    /**
     * Executing UPDATE query in database.
     * @param query
     * @param parameters
     * @return is updated
     * @throws DaoException
     */
    public boolean executeUpdateInsertQuery(String query, Object... parameters) throws DaoException {
        int updatedRows = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            setParametersInPreparedStatement(preparedStatement, parameters);
            updatedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return updatedRows > 0;
    }

    /**
     * Executing SELECT query with function.
     * @param query
     * @param parameters
     * @return Number
     * @throws DaoException
     */
    public Number executeSelectFunctionQuery(String query, Object... parameters) throws DaoException {
        Number result = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            setParametersInPreparedStatement(preparedStatement, parameters);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result = (Number) resultSet.getObject(1);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return result;
    }

    /**
     * Forming statement from prepared statement and parameters.
     * @param statement
     * @param parameters
     * @throws SQLException
     */
    private void setParametersInPreparedStatement(PreparedStatement statement, Object... parameters) throws
            SQLException {
        for (int i = 1; i <= parameters.length; i++) {
            statement.setObject(i, parameters[i - 1]);
        }
    }

    @Override
    public List<T> findAll() throws DaoException {
        return executeSelectQuery("select * from " + tableName + ";");
    }

    @Override
    public Optional<T> findById(Long id) throws DaoException {
        return executeSelectQueryForSingleObject("select * from " + tableName + " where id = ?;", id);
    }

    @Override
    public boolean update(T entity) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        return executeUpdateInsertQuery("update " + tableName + " set is_deleted = true where id = ?;", id);

    }

    @Override
    public boolean add(T t) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int countAll() throws DaoException {
        return executeSelectFunctionQuery("select count(*) from " + tableName + " where is_deleted = false;").intValue();
    }

}
