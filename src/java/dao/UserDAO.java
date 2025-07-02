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

    public User checkUserExist(String email, String password) {
        User user = null;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "  SELECT [id], [name], [email], [password], [role], [status]\n"
                        + "  FROM [dbo].[users]\n"
                        + "  WHERE [email] = ? AND [password] = ? COLLATE Latin1_General_CS_AS";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, email);
                pst.setString(2, password);
                ResultSet rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int id = rs.getInt("id");
                    String e = rs.getString("email");
                    String name = rs.getString("name");
                    String role = rs.getString("role");
                    String status = rs.getString("status");

                    user = new User(id, name, e, password, role, status);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user; // null nếu không tồn tại
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
