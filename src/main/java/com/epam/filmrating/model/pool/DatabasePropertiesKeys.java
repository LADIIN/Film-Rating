package com.epam.filmrating.model.pool;

public enum DatabasePropertiesKeys {
    DRIVER_KEY("db.driver"),
    USERNAME_KEY("db.user"),
    PASSWORD_KEY("db.password"),
    CONNECTION_POOL_SIZE_KEY("db.poolSize"),
    URL_KEY("db.url");

    private final String value;

    DatabasePropertiesKeys(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
