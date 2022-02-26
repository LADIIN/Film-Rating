package com.epam.filmrating.model.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.epam.filmrating.model.pool.DatabasePropertiesReader.*;

public class ConnectionFactory {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionFactory.class);

    static {
        try {
            Class.forName(DatabasePropertiesReader.DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("Driver class isn't found, it can't be registered", e);
            throw new RuntimeException("Driver class isn't found", e);
        }
    }

    public static Connection create() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
    }
}
