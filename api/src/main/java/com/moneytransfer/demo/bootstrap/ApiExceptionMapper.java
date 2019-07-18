package com.moneytransfer.demo.bootstrap;

import com.moneytransfer.demo.data.exception.ApiException;
import com.moneytransfer.demo.data.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Slf4j
public class ApiExceptionMapper implements ExceptionMapper<ApiException> {

    @Override
    public Response toResponse(ApiException exception) {
        ErrorResponse errorResponse = new ErrorResponse(MDC.get("id"), exception.getClass().getName(), exception.getMessage(),
                exception.getDescription(), exception.getStatus().getStatusCode(), exception.getErrorCode());
        log.error("Caught Exception, response {}", errorResponse, exception.getErrorCode());
        return Response.status(exception.getStatus())
                .entity(errorResponse).type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}