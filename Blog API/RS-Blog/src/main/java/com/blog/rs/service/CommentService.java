package com.blog.rs.service;

import com.blog.rs.Util.DBConnection;
import com.blog.rs.models.Comment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommentService {
    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comment";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Comment post = new Comment();
                post.setId(resultSet.getInt("id"));
                post.setPostId(resultSet.getInt("post_id"));
                post.setEmail(resultSet.getString("email"));
                post.setBody(resultSet.getString("body"));
                comments.add(post);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return comments;
    }
    
    
     public Comment getCommentById(int id) {
        String sql = "SELECT * FROM comment WHERE id = ?";
        Comment comment = new Comment();

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    comment.setId(rs.getInt("id"));
                    comment.setPostId(rs.getInt("post_id"));
                    comment.setEmail(rs.getString("email"));
                    comment.setBody(rs.getString("body"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return comment;
    }

    public Comment addComment(Comment comment) {
        String insert = "INSERT INTO comment(post_id, email, body) values(?,?,?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(insert);) {
            ps.setInt(1, comment.getPostId());
            ps.setString(2, comment.getEmail());
            ps.setString(3, comment.getBody());
            ps.executeUpdate();
            return comment;
        } catch (SQLException ex) {
            Logger.getLogger(CommentService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public Comment updateComment(int id, Comment comment) {
        String sql = "UPDATE comment SET email = ?, body = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, comment.getEmail());
            stmt.setString(2, comment.getBody());
            stmt.setInt(3, id);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Comment updated successfully");
            } else {
                throw new SQLException("Comment update failed. Post not found.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return comment;
    }
    
    public void deleteComment(int id) {
        String query = "DELETE FROM comment WHERE id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement st = conn.prepareStatement(query)) {

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
    
    public List<Comment> getAllCommentsPaginated(int start, int size) {
        ArrayList<Comment> commentList = new ArrayList<Comment>(getAllComments());
        if (start + size > commentList.size()) {
            return new ArrayList<Comment>();
        }
        return commentList.subList(start, start + size);
    }
}
