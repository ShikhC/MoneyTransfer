package com.moneytransfer.demo.data.repository.exception;

import com.moneytransfer.demo.data.exception.ApiException;

import javax.ws.rs.core.Response;

public class UserNotFoundInDatabaseException extends ApiException {
    public UserNotFoundInDatabaseException(String id) {
        super(Response.Status.BAD_REQUEST, "User Not Found In Database : " + id);
    }

    public UserNotFoundInDatabaseException(Response.Status status, String message, String errorCode) {
        super(status, message, errorCode);
    }

    public UserNotFoundInDatabaseException(Response.Status status, Throwable cause) {
        super(status, cause);
    }

    public UserNotFoundInDatabaseException(Response.Status status, String message, String description, String errorCode) {
        super(status, message, description, errorCode);
    }
}
