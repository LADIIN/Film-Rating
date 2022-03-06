package com.epam.filmrating.model.dao;

import com.epam.filmrating.exception.DaoException;
import com.epam.filmrating.model.dao.mapper.RowMapper;
import com.epam.filmrating.model.entity.Identifiable;
import com.epam.filmrating.model.pool.TransactionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public abstract class AbstractDao<T extends Identifiable> implements BasicDao<T> {
    private static final Logger LOGGER = LogManager.getLogger(TransactionManager.class);
    private static final String GENERATED_KEY = "GENERATED_KEY";

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

    public boolean executeUpdateInsertQuery(String query, Object... parameters) throws DaoException {
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


    public int executeSelectFunctionQuery(String query, Object... parameters) throws DaoException {
        int result = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            setParametersInPreparedStatement(preparedStatement, parameters);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e.getMessage());
        }
        return result;
    }

//    public long executeInsertQuery(String query, Object... parameters) throws DaoException {
//        long generatedId = 0;
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
//            setParametersInPreparedStatement(preparedStatement, parameters);
//            preparedStatement.executeUpdate();
//            ResultSet resultSet = preparedStatement.getGeneratedKeys();
//            if (resultSet.next()) {
//                generatedId = resultSet.getLong(GENERATED_KEY);
//            }
//        } catch (SQLException e) {
//            throw new DaoException(e.getMessage());
//        }
//
//        return generatedId;
//    }

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
        return executeQueryForSingleObject("select * from " + tableName + " where id = ?;", id);
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
        return executeSelectFunctionQuery("select count(*) from " + tableName + " where is_deleted = false;");
    }

}
