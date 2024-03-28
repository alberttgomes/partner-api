package com.eco.exception;

/**
 * @author Albert Gomes Cabral
 */
public class UnableToProcessNotifyException extends Exception {
    public UnableToProcessNotifyException(String message) {
        super(message);
    }

    public UnableToProcessNotifyException(String message, Exception exception) {
        super(message, exception);
    }
}
