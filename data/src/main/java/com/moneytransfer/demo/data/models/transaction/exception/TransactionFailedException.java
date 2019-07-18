package com.moneytransfer.demo.data.models.transaction.exception;

import com.moneytransfer.demo.data.exception.ApiException;

import javax.ws.rs.core.Response;

public class TransactionFailedException extends ApiException {

    public TransactionFailedException(Response.Status status, String message) {
        super(status, message);
    }

    public TransactionFailedException(Response.Status status, String message, String errorCode) {
        super(status, message, errorCode);
    }

    public TransactionFailedException(Response.Status status, Throwable cause) {
        super(status, cause);
    }

    public TransactionFailedException(Response.Status status, String message, String description, String errorCode) {
        super(status, message, description, errorCode);
    }
}
