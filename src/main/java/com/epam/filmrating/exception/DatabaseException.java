package com.epam.filmrating.exception;

/**
 * RuntimeException wrapper that may be appeared while getting database connection.
 *
 * @author Darkovich Vladislav
 */
public class DatabaseException extends RuntimeException {
    /**
     * Constructor.
     */
    public DatabaseException() {
        super();
    }
    /**
     * Constructor with message.
     *
     * @param message
     */
    public DatabaseException(String message) {
        super(message);
    }
    /**
     * Constructor with message and cause.
     *
     * @param message
     * @param cause
     */
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     Constructor     *
     * @param cause
     */
    public DatabaseException(Throwable cause) {
        super(cause);
    }
}
