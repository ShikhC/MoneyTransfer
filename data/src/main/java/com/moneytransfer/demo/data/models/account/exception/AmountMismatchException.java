package com.moneytransfer.demo.data.models.account.exception;

import com.moneytransfer.demo.data.exception.ApiException;

import javax.ws.rs.core.Response;

public class AmountMismatchException extends ApiException {

    public AmountMismatchException(Response.Status status, String message) {
        super(status, message);
    }

    public AmountMismatchException(Response.Status status, String message, String errorCode) {
        super(status, message, errorCode);
    }

    public AmountMismatchException(Response.Status status, Throwable cause) {
        super(status, cause);
    }

    public AmountMismatchException(Response.Status status, String message, String description, String errorCode) {
        super(status, message, description, errorCode);
    }
}
