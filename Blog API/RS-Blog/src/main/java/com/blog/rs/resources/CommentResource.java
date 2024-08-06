package com.blog.rs.resources;

import com.blog.rs.models.Comment;
import com.blog.rs.models.User;
import com.blog.rs.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
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


@Path("/comments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CommentResource {
    CommentService service = new CommentService();
    
    @GET
    @Operation(
        summary = "Get All Comment",
        description = "Retrieve a list of all comment or a paginated subset of users.",
        parameters = {
            @Parameter(name = "start", description="The Starting index for pagination", required = false, example="0"),
            @Parameter(name = "size", description = "The number of comments to retrieve", required = false, example="10")    
        }
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
        @ApiResponse(responseCode = "404", description = "No comments found")
    })
    public Response getAllComments(@QueryParam("start")int start, @QueryParam("size")int size){

        if (service.getAllComments().isEmpty()) {

            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No Comment Found")
                    .build();
        }

        if (start > 0 && size > 0) {
            if (service.getAllCommentsPaginated(start, size).isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No Comment Found")
                        .build();
            }
            System.out.println("comment resource getting paginated commments");
            return Response.ok(service.getAllCommentsPaginated(start, size)).build();
        }
        System.out.println("comment resource getting all comments");
        return Response.ok(service.getAllComments()).build();
        
    } 

    
    @GET
    @Path("/{commentId}")
    @Operation(
            summary = "Get Comment By Id",
            description = "Retrieve one comment by id",
            parameters = {
                @Parameter(name="commentId", description = "The comment id", required = true, example="21")
            }
    )
    @ApiResponses(value={
        @ApiResponse(responseCode="200", description = "Successfuly retrieved comment by id"),
        @ApiResponse(responseCode="400", description = "Invalid id format"),
        @ApiResponse(responseCode="404", description= "comment not found")
    })
    public Response getCommentById(@PathParam("commentId") String commentId){
        int id;

        try {
            id = Integer.parseInt(commentId);
        } catch (NumberFormatException nfe) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid comment ID: " + commentId)
                    .build();
        }

        System.out.println("Comment resource getting user with id: " + id);
        Comment comment = service.getCommentById(id);

        if (comment.getId() == 0 || comment == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Comment not found with id: " + id)
                    .build();
        } else {
            return Response.status(Response.Status.FOUND)
                    .entity(comment)
                    .build();
        }
    }
    
    @POST
    @Operation(
            summary = "Add new comment",
            description = "Create new commment with provided details",
            requestBody = @RequestBody(
                    description = "Comment object to be added",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = Comment.class)
                    )
            )
    )
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description = "Comment successfuly created"),
        @ApiResponse(responseCode="400", description = "Invalid comment format"),
    })
    public Response addComment(Comment comment){
        System.out.println("Comment resource adding comment...");
        Comment createdComment = service.addComment(comment);
        if(comment == null || createdComment == null ){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Comment could not be added. post must not be valid")
                    .build();
        }else{
            System.out.println(comment.getEmail());
            return status(Response.Status.CREATED).
                entity(createdComment).
                build();
        }
    }
    
    @PUT
    @Path("/{commentId}")
    @Operation(
            summary = "Update comment by id",
            description = "Update comment details of existing comment using comment id",
            requestBody = @RequestBody(
                    description = "Updated comment object",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = Comment.class)
                    )
            )
    )
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description = "Comment successfuly updated"),
        @ApiResponse(responseCode="400", description = "Invalid comment format"),
        @ApiResponse(responseCode="404", description = "Comment not found")
    })
    public Response updatePost(@PathParam("commentId") String commentId, Comment comment){
        int id;

        try {
            id = Integer.parseInt(commentId);
        } catch (NumberFormatException nfe) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid comment ID: " + commentId)
                    .build();
        }
        
        
        comment = service.updateComment(id, comment);
        
        if (comment == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Comment not found with id: " + id)
                    .build();
        } else {
            return Response.ok(comment).build();
        }
    }
    
    
    @DELETE
    @Path("/{commentId}")
    @Operation(
            summary = "Delete Comment By Id",
            description = "Delete comment from database using comment id",
            parameters = {
                @Parameter(name="commentId", description = "The comment id", required = true, example="21")
            }
    )
    @ApiResponses(value={
        @ApiResponse(responseCode="200", description = "Successfuly deleted comment by id"),
        @ApiResponse(responseCode="400", description = "Invalid id format"),
        @ApiResponse(responseCode="404", description= "comment not found")
    })
    public Response deletePost(@PathParam("commentId") String commentId){
        int id;

        try {
            id = Integer.parseInt(commentId);
        } catch (NumberFormatException nfe) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid Comment ID: " + commentId)
                    .build();
        }
        
        Comment comment = service.getCommentById(id);

        if (comment.getId() == 0 || comment == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Comment not found with id: " + id)
                    .build();
        }else{
            service.deleteComment(id);
            return Response.ok("Comment with id "  + id +  " deleted successfully").build();
        }
    }
    
}
