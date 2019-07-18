package com.moneytransfer.demo.data.exception;

import lombok.Data;

import javax.ws.rs.core.Response;

@Data
public class ApiException extends RuntimeException {
    private Response.Status status;
    private String message;
    private String description;
    private String errorCode;

    public ApiException(Response.Status status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public ApiException(Response.Status status, String message, String errorCode) {
        super(message);
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
    }

    public ApiException(Response.Status status, Throwable cause) {
        super(cause);
        this.status = status;
        this.message = cause != null ? cause.getMessage() : "";
    }

    public ApiException(Response.Status status, String message, String description, String errorCode) {
        super(message);
        this.status = status;
        this.message = message;
        this.description = description;
        this.errorCode = errorCode;
    }
}
