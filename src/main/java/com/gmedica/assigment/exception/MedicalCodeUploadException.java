package com.gmedica.assigment.exception;

public class MedicalCodeUploadException extends MedicalCodeBaseException {

    public MedicalCodeUploadException(String message) {
        super(message);
    }

    public MedicalCodeUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
