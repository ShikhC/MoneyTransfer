package com.moneytransfer.demo.data.repository.exception;

import com.moneytransfer.demo.data.exception.ApiException;

import javax.ws.rs.core.Response;

public class AccountAlreadyExistInDatabaseException extends ApiException {
    public AccountAlreadyExistInDatabaseException(String id) {
        super(Response.Status.BAD_REQUEST, "Account Already Exist In Database : " + id);
    }

    public AccountAlreadyExistInDatabaseException(Response.Status status, String message, String errorCode) {
        super(status, message, errorCode);
    }

    public AccountAlreadyExistInDatabaseException(Response.Status status, Throwable cause) {
        super(status, cause);
    }

    public AccountAlreadyExistInDatabaseException(Response.Status status, String message, String description, String errorCode) {
        super(status, message, description, errorCode);
    }
}
