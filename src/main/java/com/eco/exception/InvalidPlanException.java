package com.eco.exception;

/**
 * @author Albert Gomes Cabral
 */
public class InvalidPlanException extends RuntimeException{
    public InvalidPlanException(String message) {
        super(message);
    }
    public InvalidPlanException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
