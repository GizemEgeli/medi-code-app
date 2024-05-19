package com.gmedica.assigment.exception;

public class MedicalCodeServiceException extends MedicalCodeBaseException {
    public MedicalCodeServiceException(String message) {
        super(message);
    }

    public MedicalCodeServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
