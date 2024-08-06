package com.blog.rs.resources;

import com.blog.rs.models.Post;
import com.blog.rs.service.PostService;
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


@Path("/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {
    PostService service = new PostService();
    
    @GET
    @Operation(
        summary = "Get All Posts",
        description = "Retrieve a list of all posts or a paginated subset of posts.",
        parameters = {
            @Parameter(name = "start", description="The Starting index for pagination", required = false, example="0"),
            @Parameter(name = "size", description = "The number of posts to retrieve", required = false, example="10")    
        }
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
        @ApiResponse(responseCode = "404", description = "No posts found")
    })
    public Response getAllPosts(@QueryParam("start")int start, @QueryParam("size")int size){
        
        

        if (service.getAllPosts().isEmpty()) {

            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No Posts Found")
                    .build();
        }

        if (start > 0 || size > 0) {
            if (service.getAllPostsPaginated(start, size).isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No Posts Found")
                        .build();
            }
            System.out.println("comment resource getting paginated posts");
            return Response.ok(service.getAllPostsPaginated(start, size)).build();
        }
        System.out.println("post resource getting all posts");
        return Response.ok(service.getAllPosts()).build();
        
    } 
    
    @GET
    @Path("/{postId}")
    @Operation(
            summary = "Get Posts By Id",
            description = "Retrieve one post by id",
            parameters = {
                @Parameter(name="postId", description = "The post id", required = true, example="21")
            }
    )
    @ApiResponses(value={
        @ApiResponse(responseCode="200", description = "Successfuly retrieved post by id"),
        @ApiResponse(responseCode="400", description = "Invalid id format"),
        @ApiResponse(responseCode="404", description= "post not found")
    })
    public Response getPostById(@PathParam("postId") String postId){
        int id;

        try {
            id = Integer.parseInt(postId);
        } catch (NumberFormatException nfe) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid post ID: " + postId)
                    .build();
        }

        System.out.println("Post resource getting user with id: " + id);
        Post post = service.getPostById(id);

        if (post.getId() == 0 || post == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Post not found with id: " + id)
                    .build();
        } else {
            return Response.status(Response.Status.FOUND)
                    .entity(post)
                    .build();
        }
    }
    
    @POST
    @Operation(
            summary = "Add new post",
            description = "Create new post with provided details",
            requestBody = @RequestBody(
                    description = "Post object to be added",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = Post.class)
                    )
            )
    )
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description = "post successfuly created"),
        @ApiResponse(responseCode="400", description = "Invalid post format"),
    })
    public Response addPost(Post post){
        System.out.println("Post resource adding post...");
        Post createdPost = service.addPost(post);
        if(post == null || createdPost == null ){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Post could not be added. user must not be valid")
                    .build();
        }else{
            System.out.println(post.getTitle());
            return status(Response.Status.CREATED).
                entity(createdPost).
                build();
        }
    }
    
    @PUT
    @Path("/{postId}")
    @Operation(
            summary = "Update post by id",
            description = "Update post details of existing post using post id",
            requestBody = @RequestBody(
                    description = "Updated post object",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = Post.class)
                    )
            )
    )
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description = "Post successfuly updated"),
        @ApiResponse(responseCode="400", description = "Invalid post format"),
        @ApiResponse(responseCode="404", description = "Post not found")
    })
    public Response updatePost(@PathParam("postId") String postId, Post post){
        int id;

        try {
            id = Integer.parseInt(postId);
        } catch (NumberFormatException nfe) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid post ID: " + postId)
                    .build();
        }
        
        
        post = service.updatePost(id, post);
        
        if (post == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Post not found with id: " + id)
                    .build();
        } else {
            return Response.ok(post).build();
        }
    }
    
    @DELETE
    @Path("/{postId}")
    @Operation(
            summary = "Delete Post By Id",
            description = "Delete post from database using post id",
            parameters = {
                @Parameter(name="postId", description = "The post id", required = true, example="21")
            }
    )
    @ApiResponses(value={
        @ApiResponse(responseCode="200", description = "Successfuly deleted post by id"),
        @ApiResponse(responseCode="400", description = "Invalid id format"),
        @ApiResponse(responseCode="404", description= "post not found")
    })
    public Response deletePost(@PathParam("postId") String postId){
        int id;

        try {
            id = Integer.parseInt(postId);
        } catch (NumberFormatException nfe) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid post ID: " + postId)
                    .build();
        }
        
        Post post = service.getPostById(id);

        if (post.getId() == 0 || post == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Post not found with id: " + id)
                    .build();
        }else{
            service.deletePost(id);
            return Response.ok("Post with id "  + id +  " deleted successfully").build();
        }
    }
    
}
