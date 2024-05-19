package com.gmedica.assigment.exception;

public class MedicalCodeParseException extends MedicalCodeBaseException {

    public MedicalCodeParseException(String message) {
        super(message);
    }

    public MedicalCodeParseException(String message, Throwable cause) {
        super(message, cause);
    }

}