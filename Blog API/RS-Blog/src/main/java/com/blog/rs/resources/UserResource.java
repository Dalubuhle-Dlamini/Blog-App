package com.blog.rs.resources;

import com.blog.rs.models.User;
import com.blog.rs.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import static jakarta.ws.rs.core.Response.status;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    UserService service = new UserService();
    
    @GET
    @Operation(
        summary = "Get All Users",
        description = "Retrieve a list of all users or a paginated subset of users.",
        parameters = {
            @Parameter(name = "start", description="The Starting index for pagination", required = false, example="0"),
            @Parameter(name = "size", description = "The number of users to retrieve", required = false, example="10")    
        }
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
        @ApiResponse(responseCode = "404", description = "No users found")
    })
    public Response getAllUsers(@QueryParam("start") int start, @QueryParam("size") int size) {

        if (service.getAllUsers().isEmpty()) {

            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No Users Found")
                    .build();
        }

        if (start > 0 || size > 0) {
            if (service.getAllUsersPaginated(start, size).isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No Users Found")
                        .build();
            }
            System.out.println("post resource getting paginated user");
            return Response.ok(service.getAllUsersPaginated(start, size)).build();
        }
        System.out.println("user resource getting all users...");
        return Response.ok(service.getAllUsers()).build();
    }

    @GET
    @Path("/{userId}")
    @Operation(
            summary = "Get User By Id",
            description = "Retrieve one user by id",
            parameters = {
                @Parameter(name="userId", description = "The user id", required = true, example="21")
            }
    )
    @ApiResponses(value={
        @ApiResponse(responseCode="200", description = "Successfuly retrieved user by id"),
        @ApiResponse(responseCode="400", description = "Invalid id format"),
        @ApiResponse(responseCode="404", description= "user not found")
    })
    public Response getUserById(@PathParam("userId") String userId) {
        int id;

        try {
            id = Integer.parseInt(userId);
        } catch (NumberFormatException nfe) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid user ID: " + userId)
                    .build();
        }

        System.out.println("user resource getting user with id: " + id);
        User user = service.getUserById(id);

        if (user.getId() == 0 || user == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("User not found with id: " + id)
                    .build();
        } else {
            return Response.status(Response.Status.FOUND)
                    .entity(user)
                    .build();
        }
    }

    @POST
    @Operation(
            summary = "Add new user",
            description = "Create new user with provided details",
            requestBody = @RequestBody(
                    description = "User object to be added",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = User.class)
                    )
            )
    )
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description = "User successfuly created"),
        @ApiResponse(responseCode="400", description = "Invalid user format"),
    })
    public Response addUser(User user) {
        System.out.println("user resource adding user...");
        User createdUser = service.addUser(user);
        if (user == null || createdUser == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Sorry user could not be added")
                    .build();
        } else {
            return status(Response.Status.CREATED).
                    entity(createdUser).
                    build();
        }
    }

    @PUT
    @Path("/{userId}")
    @Operation(
            summary = "Update user by id",
            description = "Update user details of existing user using user id",
            requestBody = @RequestBody(
                    description = "Updated user object",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = User.class)
                    )
            )
    )
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description = "User successfuly updated"),
        @ApiResponse(responseCode="400", description = "Invalid user format"),
        @ApiResponse(responseCode="404", description = "User not found")
    })
    public Response updateUser(@PathParam("userId") @DefaultValue("1") String userId, User user) {
        int id;

        try {
            id = Integer.parseInt(userId);
        } catch (NumberFormatException nfe) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid user ID: " + userId)
                    .build();
        }

        user = service.updateUser(id, user);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("User not found with id: " + id)
                    .build();
        } else {
            return Response.ok(user).build();
        }
    }

    @DELETE
    @Path("/{userId}")
    @Operation(
            summary = "Delete User By Id",
            description = "Delete user from database using user id",
            parameters = {
                @Parameter(name="userId", description = "The user id", required = true, example="21")
            }
    )
    @ApiResponses(value={
        @ApiResponse(responseCode="200", description = "Successfuly deleted user by id"),
        @ApiResponse(responseCode="400", description = "Invalid id format"),
        @ApiResponse(responseCode="404", description= "user not found")
    })
    public Response deleteUser(@PathParam("userId") String userId) {

        System.out.println("User resource deleting user with id: " + userId);
        int id;

        try {
            id = Integer.parseInt(userId);
        } catch (NumberFormatException nfe) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid user ID: " + userId)
                    .build();
        }

        User user = service.getUserById(id);

        if (user.getId() == 0 || user == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("User not found with id: " + id)
                    .build();
        } else {
            service.deleteUser(id);
            return Response.ok("User with id " + id + " deleted successfully").build();
        }

    }

}
