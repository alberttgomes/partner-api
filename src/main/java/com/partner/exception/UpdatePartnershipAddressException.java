package com.partner.exception;

/**
 * @author Albert Gomes Cabral
 */
public class UpdatePartnershipAddressException extends RuntimeException {
    public UpdatePartnershipAddressException(String message, Exception exception) {
        super(message, exception);
    }

    public UpdatePartnershipAddressException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
