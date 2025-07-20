/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import mylib.DBUtils;

/**
 *
 * @author Admin
 */
public class UserDAO {

    public User checkUser(String email, String password) {
        User user = null;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT [id]\n"
                        + "      ,[name]\n"
                        + "      ,[email]\n"
                        + "      ,[password]\n"
                        + "      ,[role]\n"
                        + "      ,[status]\n"
                        + "  FROM [library_system].[dbo].[users] WHERE [email]=? AND [password]=?;";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setString(1, email);
                st.setString(2, password);
                ResultSet table = st.executeQuery();
                if (table != null && table.next()) {
                    int id = table.getInt("id");
                    String name = table.getString("name");
                    String role = table.getString("role");
                    String status = table.getString("status");
                    user = new User(id, name, email, password, role, status);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    
    //--------------------------------- CHECK USER FOR REGISTER ---------------------------------
    public User getUserByEmail(String email) {
        try {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement ps = DBUtils.getConnection().prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getString("status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //--------------------------------- INSERT NEW REGISTER INTO DB ---------------------------------
    public void register(User user) {
        try {
            String sql = "INSERT INTO users (name, email, password, role, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = DBUtils.getConnection().prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getStatus());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
        //--------------------------------- UPDATE IN CHANGE PROFILE ---------------------------------

    public boolean updateUser(User user) {
    String sql = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";
    try (Connection conn = DBUtils.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPassword());
        ps.setInt(4, user.getId());
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}


    public User checkUserByID(int id) {
        User user = null;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT [id]\n"
                        + "      ,[name]\n"
                        + "      ,[email]\n"
                        + "      ,[password]\n"
                        + "      ,[role]\n"
                        + "      ,[status]\n"
                        + "  FROM [library_system].[dbo].[users] WHERE [id]=?;";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, id);
                ResultSet table = st.executeQuery();
                if (table != null && table.next()) {
                    String name = table.getString("name");
                    String role = table.getString("role");
                    String email = table.getString("email");
                    String password = table.getString("password");
                    String status = table.getString("status");
                    user = new User(id, name, email, password, role, status);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
