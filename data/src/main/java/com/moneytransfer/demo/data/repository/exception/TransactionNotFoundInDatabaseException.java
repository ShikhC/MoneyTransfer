package com.moneytransfer.demo.data.repository.exception;

import com.moneytransfer.demo.data.exception.ApiException;

import javax.ws.rs.core.Response;

public class TransactionNotFoundInDatabaseException extends ApiException {
    public TransactionNotFoundInDatabaseException(Response.Status status, String message) {
        super(status, message);
    }

    public TransactionNotFoundInDatabaseException(Response.Status status, String message, String errorCode) {
        super(status, message, errorCode);
    }

    public TransactionNotFoundInDatabaseException(Response.Status status, Throwable cause) {
        super(status, cause);
    }

    public TransactionNotFoundInDatabaseException(Response.Status status, String message, String description, String errorCode) {
        super(status, message, description, errorCode);
    }
}
