package com.moneytransfer.demo.data.models.user.exception;

import com.moneytransfer.demo.data.exception.ApiException;

import javax.ws.rs.core.Response;

public class UserInActiveException extends ApiException {

    public UserInActiveException(Response.Status status, String message) {
        super(status, message);
    }

    public UserInActiveException(Response.Status status, String message, String errorCode) {
        super(status, message, errorCode);
    }

    public UserInActiveException(Response.Status status, Throwable cause) {
        super(status, cause);
    }

    public UserInActiveException(Response.Status status, String message, String description, String errorCode) {
        super(status, message, description, errorCode);
    }
}
