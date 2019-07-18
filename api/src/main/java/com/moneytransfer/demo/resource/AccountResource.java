package com.moneytransfer.demo.resource;


import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.moneytransfer.demo.data.exception.ApiException;
import com.moneytransfer.demo.data.models.account.AccountDetail;
import com.moneytransfer.demo.data.models.transaction.TransactionDetails;
import com.moneytransfer.demo.service.IAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Slf4j
@Path("/account/")
@Api(value = "/account", description = "Validate Account")
public class AccountResource {

    IAccountService accountService;

    @Inject
    public AccountResource(IAccountService accountService) {
        this.accountService = accountService;
    }

    @Path("{accountId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Validate Account", notes = "Only AccountId", response = AccountDetail.class, responseContainer = "json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpStatus.SC_OK, message = "Validation Successful"),
            @ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request"),
            @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Authentication unsuccessful"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server Error")

    })
    public Response getAccountByAccountId(@PathParam("accountId") String accountId) {
        return Response.status(HttpStatus.SC_OK).entity(accountService.getAccount(accountId)).build();
    }

    @Path("{userId}/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    @ApiOperation(value = "Accept userId", notes = "Accept userId", response = AccountDetail.class,
            responseContainer = "json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. Successful."),
            @ApiResponse(code = 400, message = "Bad Request")

    })
    public Response createAccountForUser(@PathParam("userId") String userId) {
        return Response.status(HttpStatus.SC_OK).entity(accountService.createAccount(userId)).build();
    }

    @Path("{accountId}/addMoney/{amount}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    @ApiOperation(value = "add money given accountId and amount", notes = "Accept userId", response = AccountDetail.class,
            responseContainer = "json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. Successful."),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    public Response addMoneyToAccount(@PathParam("accountId") String accountId, @PathParam("amount") Double amount) {
        return Response.status(HttpStatus.SC_OK).entity(accountService.addMoneyToAccount(accountId, amount, null)).build();
    }

    @Path("{accountId}/transferMoney/{toAccountId}/{amount}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    @ApiOperation(value = "transfer money from one account to another account", notes = "Accept accountIds and amount", response = TransactionDetails.class,
            responseContainer = "json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. Successful.")
    })
    public Response transferMoney(@PathParam("accountId") String accountId, @PathParam("toAccountId") String toAccountId, @PathParam("amount") Double amount) throws ApiException {
        return Response.status(HttpStatus.SC_OK).entity(accountService.transferMoney(accountId, toAccountId, amount, null)).build();
    }
}
