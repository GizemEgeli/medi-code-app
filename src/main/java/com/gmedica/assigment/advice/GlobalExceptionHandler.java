package com.gmedica.assigment.advice;

import com.gmedica.assigment.exception.ErrorResponseDTO;
import com.gmedica.assigment.exception.MedicalCodeBaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MedicalCodeBaseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleMedicalCodeException(MedicalCodeBaseException exception) {
        return new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDTO handleException(Exception exception) {
        log.error("Unexpected error occurred", exception);
        return new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An internal error occurred. Please try again later.");
    }

}