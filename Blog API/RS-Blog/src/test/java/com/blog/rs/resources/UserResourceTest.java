//package com.blog.rs.resources;
//
//import com.blog.rs.models.User;
//import jakarta.ws.rs.client.Entity;
//import jakarta.ws.rs.core.Application;
//import jakarta.ws.rs.core.MediaType;
//import jakarta.ws.rs.core.Response;
//import static junit.framework.TestCase.assertEquals;
//import static junit.framework.TestCase.assertNotNull;
//import org.glassfish.jersey.server.ResourceConfig;
//import org.glassfish.jersey.test.JerseyTest;
//import org.glassfish.jersey.test.TestProperties;
//import org.junit.Test;
//
//public class UserResourceTest extends JerseyTest {
//
//    @Override
//    public Application configure() {
//        enable(TestProperties.LOG_TRAFFIC);
//        enable(TestProperties.DUMP_ENTITY);
//        return new ResourceConfig(UserResource.class);
//    }
//
//    @Test
//    public void testGetAllUsers() {
//        Response response = target("/users").request().get();
//        assertEquals("should return status 200", 200, response.getStatus());
//        assertNotNull("Should return user list", response.getEntity().toString());
//        System.out.println(response.getStatus());
//        System.out.println(response.readEntity(String.class));
//    }
//
//    @Test
//    public void testGetUserById() {
//        Response output = target("/users/user/100").request().get();
//        assertEquals("Should return status 200", 200, output.getStatus());
//        assertNotNull("Should return user object as json", output.getEntity());
//        System.out.println(output.getStatus());
//        System.out.println(output.readEntity(String.class));
//    }
//
//    @Test
//    public void testCreateUser() {
//        User user = new User(100, "Nyosi Mnyatsi", "Nyosi", "nyosi@gmail.com", "Mbangweni Mbabane", "+268 7814 1896");
//        Response output = target("/users").request().post(Entity.entity(user, MediaType.APPLICATION_JSON));
//        System.out.println(output.getStatus());
//        assertEquals("Should return status 201", 201, output.getStatus());
//    }
//
//    @Test
//    public void testUpdateUser() {
//        User user = new User(100, "Nyosi Mnyatsi", "Nyosisto", "nyosi@gmail.com", "Mbangweni Nhlangano", "+268 7814 1896");
//        Response output = target("/users/user/101").request().put(Entity.entity(user, MediaType.APPLICATION_JSON));
//        assertEquals("Should return status 204", 204, output.getStatus());
//        System.out.println(output.getStatus());
//    }
//
//    @Test
//    public void testDeleteUser() {
//        Response output = target("/users/user/100").request().delete();
//        assertEquals("Should return status 204", 204, output.getStatus());
//    }
//}
