package com.eco.exception;

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
