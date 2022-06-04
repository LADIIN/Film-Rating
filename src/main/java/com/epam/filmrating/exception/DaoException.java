package com.epam.filmrating.exception;

/**
 * Exception that may be appeared in Dao.
 *
 * @author Darkovich Vladislav
 */
public class DaoException extends Exception {
    /**
     * Constructor.
     */
    public DaoException() {
        super();
    }
    /**
     * Constructor with message.
     *
     * @param message
     */
    public DaoException(String message) {
        super(message);
    }
    /**
     * Constructor with message and cause.
     *
     * @param message
     * @param cause
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * Constructor with cause.
     *
     * @param cause
     */
    public DaoException(Throwable cause) {
        super(cause);
    }
}
