package com.eco.exception;

/**
 * @author Albert Gomes Cabral
 */
public class CreatePartnershipMemberException extends RuntimeException {
    public CreatePartnershipMemberException(String message, Exception exception) {
        super(message, exception);
    }

    public CreatePartnershipMemberException(String message) {
        super(message);
    }
}
