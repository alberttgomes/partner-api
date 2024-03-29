package com.partner.exception;

/**
 * @author Albert Gomes Cabral
 */
public class UnableToProcessPlatformManagerException extends RuntimeException {
    public UnableToProcessPlatformManagerException(String message) {
        super(message);
    }

    public UnableToProcessPlatformManagerException(String message, Exception exception) {
        super(message, exception);
    }
}
