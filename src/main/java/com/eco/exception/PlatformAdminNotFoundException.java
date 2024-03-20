package com.eco.exception;

/**
 * @author Albert Gomes Cabral
 */
public class PlatformAdminNotFoundException extends RuntimeException {
    public PlatformAdminNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
