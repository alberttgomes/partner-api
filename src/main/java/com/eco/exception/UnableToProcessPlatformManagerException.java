package com.eco.exception;

/**
 * @author Albert Gomes Cabral
 */
public class UnableToProcessPlatformManagerException extends Exception {
    public UnableToProcessPlatformManagerException(String message) {
        super(message);
    }

    public UnableToProcessPlatformManagerException(String message, Exception exception) {
        super(message, exception);
    }
}
