package com.epam.filmrating.model.dao;

import com.epam.filmrating.exception.DaoException;
import com.epam.filmrating.model.dao.mapper.RowMapper;
import com.epam.filmrating.model.entity.Identifiable;
import com.epam.filmrating.model.pool.TransactionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public abstract class AbstractDao<T extends Identifiable> implements BasicDao<T> {
    private static final Logger LOGGER = LogManager.getLogger(TransactionManager.class);
    private static final String SELECT_BY_ID = "SELECT * FROM ? WHERE id=?;";
    private static final String SELECT_ALL = "SELECT * FROM ?;";

    private final RowMapper<T> rowMapper;
    private final Connection connection;
    private final String tableName;

    public AbstractDao(RowMapper<T> rowMapper, Connection connection, String tableName) {
        this.rowMapper = rowMapper;
        this.connection = connection;
        this.tableName = tableName;
    }

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
            LOGGER.error("Error caused by {}", e.getMessage());
            throw new DaoException("Error caused by", e);
        }
        return list;
    }

    public Optional<T> executeQueryForSingleObject(String query, Object... parameters) throws DaoException {
        Optional<T> result = Optional.empty();
        List<T> list = executeSelectQuery(query, parameters);
        if (!list.isEmpty()) {
            result = Optional.of(list.get(0));
        }
        return result;
    }

    public boolean executeUpdateQuery(String query, Object... parameters) throws DaoException {
        int updatedRows = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            setParametersInPreparedStatement(preparedStatement, parameters);
            updatedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error caused by {}", e.getMessage());
            throw new DaoException("Error caused by", e);
        }
        return updatedRows > 0;
    }

    private void setParametersInPreparedStatement(PreparedStatement statement, Object... parameters) throws
            SQLException {
        for (int i = 1; i <= parameters.length; i++) {
            statement.setObject(i, parameters[i - 1]);
        }
    }

    @Override
    public List<T> findAll() throws DaoException {
        return executeSelectQuery(SELECT_ALL);
    }

    @Override
    public Optional<T> findById(Long id) throws DaoException {
        return executeQueryForSingleObject(SELECT_BY_ID, tableName, id);
    }

    @Override
    public boolean update(T entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException();
    }
}
