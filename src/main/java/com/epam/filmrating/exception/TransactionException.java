package com.epam.filmrating.exception;

/**
 * Exception that me be appeared when making database transaction.
 *
 * @author Darkovich Vladislav
 */
public class TransactionException extends Exception {
    /**
     * Default constructor.
     */
    public TransactionException() {
        super();
    }

    /**
     * Constructor with message.
     *
     * @param message
     */
    public TransactionException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause.
     *
     * @param message
     * @param cause
     */
    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with cause.
     *
     * @param cause
     */
    public TransactionException(Throwable cause) {
        super(cause);
    }

}
