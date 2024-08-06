package com.blog.rs.service;

import com.blog.rs.Util.DBConnection;
import com.blog.rs.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(sql); ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setPhone(rs.getString("phone"));
                users.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return users;
    }

    public User getUserById(int id) {
        User user = new User();
        String sql = "SELECT * FROM user WHERE id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("user service found user");
                if (rs.next()) {
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setAddress(rs.getString("address"));
                    user.setPhone(rs.getString("phone"));
                }

                System.out.println("Getting user by id in user service");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

    public User addUser(User user) {
        String insert = "INSERT INTO user(name, username, email, address, phone) values(?,?,?,?,?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(insert);) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getAddress());
            ps.setString(5, user.getPhone());
            ps.executeUpdate();
            
            System.out.println("user service inserted user successfuly...");
            return user;
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        
    }

    public User updateUser(int id, User user) {
        String sql = "UPDATE user SET name = ?, username = ?, email = ?, address = ?, phone = ?  WHERE id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getAddress());
            stmt.setString(5, user.getPhone());
            stmt.setInt(6, id);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User updated successfully");
            } else {
                throw new SQLException("User update failed. User not found.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

    public void deleteUser(int id) {
        String query = "DELETE FROM user WHERE id = ?";
        String query2 = "DELETE FROM post WHERE user_id = ?";
        String query3 = "DELETE FROM comment WHERE post_id IN (SELECT id FROM post WHERE user_id = ?)";
        
        
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement st = conn.prepareStatement(query);
                PreparedStatement st2 = conn.prepareStatement(query2);
                PreparedStatement st3 = conn.prepareStatement(query3);) {

            
            st3.setInt(1, id);
            st3.executeUpdate();
            
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

    public List<User> getAllUsersPaginated(int start, int size) {
        List<User> userList = getAllUsers();
        if(start < 0 || size <=0 || start >= userList.size()){
            return Collections.emptyList();
        }
        
        int end = Math.min(start + size, userList.size());
        return userList.subList(start, end);
    }
}
