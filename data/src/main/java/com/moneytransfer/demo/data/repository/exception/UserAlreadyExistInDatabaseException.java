package com.moneytransfer.demo.data.repository.exception;

import com.moneytransfer.demo.data.exception.ApiException;

import javax.ws.rs.core.Response;

public class UserAlreadyExistInDatabaseException extends ApiException {
    public UserAlreadyExistInDatabaseException(String id) {
        super(Response.Status.BAD_REQUEST, "User Already Exist In Database : " + id);
    }

    public UserAlreadyExistInDatabaseException(Response.Status status, String message, String errorCode) {
        super(status, message, errorCode);
    }

    public UserAlreadyExistInDatabaseException(Response.Status status, Throwable cause) {
        super(status, cause);
    }

    public UserAlreadyExistInDatabaseException(Response.Status status, String message, String description, String errorCode) {
        super(status, message, description, errorCode);
    }
}
