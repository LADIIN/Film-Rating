package com.epam.filmrating.exception;

/**
 * Exception that may be appeared in services.
 *
 * @author Darkovich Vladislav.
 */
public class ServiceException extends Exception{
    /**
     * Constructor.
     */
    public ServiceException() {
        super();
    }
    /**
     * Constructor with message.
     *
     * @param message
     */
    public ServiceException(String message) {
        super(message);
    }
    /**
     * Constructor with message and cause.
     *
     * @param message
     * @param cause
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * Constructor with cause.
     *
     * @param cause
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }
}
