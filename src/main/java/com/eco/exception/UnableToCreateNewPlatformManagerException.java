package com.eco.exception;

/**
 * @author Albert Gomes Cabral
 */
public class UnableToCreateNewPlatformManagerException extends Exception {
    public UnableToCreateNewPlatformManagerException(String message) {
        super(message);
    }

    public UnableToCreateNewPlatformManagerException(String message, Exception exception) {
        super(message, exception);
    }
}
