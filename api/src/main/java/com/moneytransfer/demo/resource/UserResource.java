package com.moneytransfer.demo.resource;

import com.google.inject.Inject;
import com.moneytransfer.demo.data.models.user.UserAccountsInfo;
import com.moneytransfer.demo.data.models.user.UserDetails;
import com.moneytransfer.demo.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Slf4j
@Path("/user/")
@Api(value = "/user", description = "Validate User")
public class UserResource {

    private IUserService userService;

    @Inject
    public UserResource(IUserService userService) {
        this.userService = userService;
    }

    @Path("{userId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Validate User", notes = "Only UserId", response = UserDetails.class, responseContainer = "json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpStatus.SC_OK, message = "Validation Successful"),
            @ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request"),
            @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Authentication unsuccessful"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server Error")

    })
    public Response getUserByUserId(@PathParam("userId") String userId) {
        return Response.status(HttpStatus.SC_OK).entity(userService.getUserDetails(userId)).build();
    }

    @Path("{userId}/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create User", notes = "Only UserId", response = UserDetails.class, responseContainer = "json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpStatus.SC_OK, message = "Validation Successful"),
            @ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request"),
            @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Authentication unsuccessful"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server Error")

    })
    public Response createUserByUserId(@PathParam("userId") String userId) {
        return Response.status(HttpStatus.SC_OK).entity(userService.createUser(userId)).build();
    }

    @Path("{userId}/transactions/{limit}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Requires User and count of transactions to and fro", notes = "UserId and Limit", response = Map.class, responseContainer = "json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpStatus.SC_OK, message = "Validation Successful"),
            @ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request"),
            @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Authentication unsuccessful"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server Error")

    })
    public Response getUserTransactions(@PathParam("userId") String userId, @PathParam("limit") Integer limit) {
        return Response.status(HttpStatus.SC_OK).entity(userService.getUserTransactions(userId, limit)).build();
    }

    @Path("{userId}/detailed")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Requires User and count of transactions to and fro", notes = "UserId and Limit", response = UserAccountsInfo.class, responseContainer = "json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpStatus.SC_OK, message = "Validation Successful"),
            @ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request"),
            @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Authentication unsuccessful"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Internal Server Error")

    })
    public Response getUserAccountsInfo(@PathParam("userId") String userId) {
        return Response.status(HttpStatus.SC_OK).entity(userService.getUserAccountInfo(userId)).build();
    }
}
