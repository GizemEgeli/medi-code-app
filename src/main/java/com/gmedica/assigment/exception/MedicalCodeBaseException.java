package com.gmedica.assigment.exception;

public class MedicalCodeBaseException extends RuntimeException {
    public MedicalCodeBaseException(String message) {
        super(message);
    }
    public MedicalCodeBaseException(String message, Throwable cause) {
        super(message, cause);
    }
}