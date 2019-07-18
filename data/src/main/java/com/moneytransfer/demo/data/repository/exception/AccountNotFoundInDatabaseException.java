package com.moneytransfer.demo.data.repository.exception;

import com.moneytransfer.demo.data.exception.ApiException;

import javax.ws.rs.core.Response;

public class AccountNotFoundInDatabaseException extends ApiException {
    public AccountNotFoundInDatabaseException(String id) {
        super(Response.Status.BAD_REQUEST, "Account Not Found In Database : " + id);
    }

    public AccountNotFoundInDatabaseException(Response.Status status, String message, String errorCode) {
        super(status, message, errorCode);
    }

    public AccountNotFoundInDatabaseException(Response.Status status, Throwable cause) {
        super(status, cause);
    }

    public AccountNotFoundInDatabaseException(Response.Status status, String message, String description, String errorCode) {
        super(status, message, description, errorCode);
    }
}
