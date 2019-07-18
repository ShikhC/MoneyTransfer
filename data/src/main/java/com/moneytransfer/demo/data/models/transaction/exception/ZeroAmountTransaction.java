package com.moneytransfer.demo.data.models.transaction.exception;

import com.moneytransfer.demo.data.exception.ApiException;

import javax.ws.rs.core.Response;

public class ZeroAmountTransaction extends ApiException {
    public ZeroAmountTransaction(Response.Status status, String message) {
        super(status, message);
    }

    public ZeroAmountTransaction(Response.Status status, String message, String errorCode) {
        super(status, message, errorCode);
    }

    public ZeroAmountTransaction(Response.Status status, Throwable cause) {
        super(status, cause);
    }

    public ZeroAmountTransaction(Response.Status status, String message, String description, String errorCode) {
        super(status, message, description, errorCode);
    }
}
