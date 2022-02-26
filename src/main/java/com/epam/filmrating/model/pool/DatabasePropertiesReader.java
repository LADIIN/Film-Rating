package com.epam.filmrating.model.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class DatabasePropertiesReader {
    private static final Logger LOGGER = LogManager.getLogger(DatabasePropertiesReader.class);
    private static final String DATABASE_PROPERTIES_FILE = "database";
    static final String DRIVER;
    static final String USERNAME;
    static final String PASSWORD;
    static final int CONNECTION_POOL_SIZE;
    static final String DATABASE_URL;

    static {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(DATABASE_PROPERTIES_FILE);
            DRIVER = resourceBundle.getString(DatabasePropertiesKeys.DRIVER_KEY.getValue());
            USERNAME = resourceBundle.getString(DatabasePropertiesKeys.USERNAME_KEY.getValue());
            PASSWORD = resourceBundle.getString(DatabasePropertiesKeys.PASSWORD_KEY.getValue());
            String poolSizeString = resourceBundle.getString(DatabasePropertiesKeys.CONNECTION_POOL_SIZE_KEY.getValue());
            CONNECTION_POOL_SIZE = Integer.parseInt(poolSizeString);
            DATABASE_URL = resourceBundle.getString(DatabasePropertiesKeys.URL_KEY.getValue());
        } catch (MissingResourceException e) {
            LOGGER.fatal("File or key isn't not found.");
            throw new RuntimeException("File or key isn't found", e);
        }
    }


}
