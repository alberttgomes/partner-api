package com.eco.exception;

/**
 * @author Albert Gomes Cabral
 */
public class PlatformException extends RuntimeException {
    public PlatformException(String message) {
        super(message);
    }
    public PlatformException(String message, Exception exception) {
        super(message, exception);
    }

    public PlatformException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
