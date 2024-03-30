package com.partner.exception;

/**
 * @author Albert Gomes Cabral
 */
public class UnableToProcessPlanException extends RuntimeException {
    public UnableToProcessPlanException(String message) {
        super(message);
    }
    public UnableToProcessPlanException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
