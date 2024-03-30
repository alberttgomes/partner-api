package com.partner.exception;

/**
 * @author Albert Gomes Cabral
 */
public class PartnershipMemberNotFoundException extends RuntimeException {
    public PartnershipMemberNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    };

    public PartnershipMemberNotFoundException(String message) {
        super(message);
    }

    public PartnershipMemberNotFoundException(Throwable throwable) {
        super(throwable);
    };
}
