package com.example.salesmanagementsystem.exception;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException{
    private HttpStatus httpStatus;
    private String message;

    public AppException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public AppException(String message, HttpStatus httpStatus, String message1) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message1;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
