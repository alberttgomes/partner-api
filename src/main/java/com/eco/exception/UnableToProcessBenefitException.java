package com.partner.exception;

/**
 * @author Albert Gomes Cabral
 */
public class UnableToProcessBenefitException extends Exception {
    public UnableToProcessBenefitException(String message) {
        super(message);
    }

    public UnableToProcessBenefitException(String message, Exception exception) {
        super(message, exception);
    }

    public UnableToProcessBenefitException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
