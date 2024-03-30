package com.partner.exception;

/**
 * @author Albert Gomes Cabral
 */
public class PartnershipPermissionDeniedException extends RuntimeException {
    public PartnershipPermissionDeniedException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public PartnershipPermissionDeniedException(String message) {
        super(message);
    }
}
