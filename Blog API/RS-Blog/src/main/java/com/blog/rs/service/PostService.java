package com.blog.rs.service;

import com.blog.rs.Util.DBConnection;
import com.blog.rs.models.Post;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostService {

    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM post";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Post post = new Post();
                post.setId(resultSet.getInt("id"));
                post.setUserId(resultSet.getInt("user_id"));
                post.setTitle(resultSet.getString("title"));
                post.setBody(resultSet.getString("body"));
                posts.add(post);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return posts;
    }

    public Post getPostById(int id) {
        String sql = "SELECT * FROM post WHERE id = ?";
        Post post = new Post();

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    post.setId(rs.getInt("id"));
                    post.setUserId(rs.getInt("user_id"));
                    post.setTitle(rs.getString("title"));
                    post.setBody(rs.getString("body"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return post;
    }

    public Post addPost(Post post) {
        String insert = "INSERT INTO post(user_id, title, body) values(?,?,?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(insert);) {
            ps.setInt(1, post.getUserId());
            ps.setString(2, post.getTitle());
            ps.setString(3, post.getBody());
            ps.executeUpdate();
            return post;
        } catch (SQLException ex) {
            Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public Post updatePost(int id, Post post) {
        String sql = "UPDATE post SET title = ?, body = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, post.getTitle());
            stmt.setString(2, post.getBody());
            stmt.setInt(3, id);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Post {0} updated successfully");
            } else {
                throw new SQLException("Post update failed. User not found.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return post;
    }

    public void deletePost(int id) {
        String query = "DELETE FROM post WHERE id = ?";
        String query2 = "DELETE FROM comment WHERE post_id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement st = conn.prepareStatement(query); PreparedStatement st2 = conn.prepareStatement(query2)) {

            st2.setInt(1, id);
            st2.executeUpdate();

            st.setInt(1, id);
            st.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("SQL Exception " + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected Exception " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Post> getAllPostsPaginated(int start, int size) {
        ArrayList<Post> postList = new ArrayList<Post>(getAllPosts());
        if (start + size > postList.size()) {
            return new ArrayList<Post>();
        }
        return postList.subList(start, start + size);
    }

    public List<Post> filterByName(String name) {
        String sql = "SELECT post.id, post.user_id, post.title, post.body FROM post JOIN user ON post.user_id = user.id WHERE user.username = ?";
        List<Post> posts = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                Post post = new Post();
                while (rs.next()) {
                    post.setId(rs.getInt("id"));
                    post.setUserId(rs.getInt("user_id"));
                    post.setTitle(rs.getString("title"));
                    post.setBody(rs.getString("body"));
                    posts.add(post);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return posts;
    }
}
