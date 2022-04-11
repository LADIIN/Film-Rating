package com.epam.filmrating.model.pool;

import com.epam.filmrating.exception.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Creates database connection.
 */
public class ConnectionFactory {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionFactory.class);
    private static final String DRIVER_KEY = "db.driver";
    private static final String USERNAME_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";
    private static final String URL_KEY = "db.url";
    private static final String PROPERTIES_PATH = "C:\\Users\\ACER\\IdeaProjects\\Java Development Course\\Film-Rating\\src\\main\\resources\\database.properties";

    private static final String databaseUrl;
    private static final String username;
    private static final String password;

    static {
        try {
            FileReader fileReader = new FileReader(PROPERTIES_PATH);
            Properties properties = new Properties();
            properties.load(fileReader);
            String driver = properties.getProperty(DRIVER_KEY);
            Class.forName(driver);
            databaseUrl = properties.getProperty(URL_KEY);
            username = properties.getProperty(USERNAME_KEY);
            password = properties.getProperty(PASSWORD_KEY);
        } catch (ClassNotFoundException | IOException e) {
            LOGGER.fatal("Driver class isn't found", e);
            throw new DatabaseException("Driver class isn't found", e);
        }
    }

    public Connection create() throws SQLException {
        return DriverManager.getConnection(databaseUrl, username, password);
    }
}
