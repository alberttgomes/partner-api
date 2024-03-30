package com.partner.exception;

/**
 * @author Albert Gomes Cabral
 */
public class UpdatePartnershipMemberException extends RuntimeException {
    public UpdatePartnershipMemberException(String message, Exception exception) {
        super(message, exception);
    }

    public UpdatePartnershipMemberException(String message) {
        super(message);
    }
}
