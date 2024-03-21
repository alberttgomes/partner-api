package com.eco.exception;

/**
 * @author Albert Gomes Cabral
 */
public class PlatformManagerNotFoundException extends RuntimeException {
    public PlatformManagerNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
