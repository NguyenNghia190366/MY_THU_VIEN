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

    //Ham nay de update user's name and pass by id and return integer
    public int UpdateUser(int id, String name, String password) {
        int result = 0;
        Connection cn = null;
        PreparedStatement pst = null;

        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "UPDATE [dbo].[users] SET [name] = ?, [password] = ? WHERE [id] = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, name);
                pst.setString(2, password);
                pst.setInt(3, id);

                result = pst.executeUpdate();  // Trả về số dòng bị ảnh hưởng
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    //--------------------------------- UPDATE IN CHANGE PROFILE ---------------------------------
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
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

    public User showUserByEmail(String email) {
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
                        + "  FROM [library_system].[dbo].[users] WHERE [email]=?;";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setString(1, email);
                ResultSet table = st.executeQuery();
                if (table != null && table.next()) {
                    String name = table.getString("name");
                    String role = table.getString("role");
                    int id = table.getInt("id");
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

    public int changeStatusUser(int id) {
        int re = 0;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT "
                        + "      [status]\n"
                        + "  FROM [library_system].[dbo].[users] WHERE [id]=?;";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, id);
                ResultSet table = st.executeQuery();
                if (table != null && table.next()) {
                    String status = table.getString("status");
                    String sql2 = "UPDATE [library_system].[dbo].[users] SET [status] = ? WHERE [id] = ?";
                    PreparedStatement st2 = cn.prepareStatement(sql2);
                    if (status.equals("active")) {
                        st2.setString(1, "blocked");
                    } else if (status.equals("blocked")) {
                        st2.setString(1, "active");
                    }
                    st2.setInt(2, id);
                    re = st2.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return re;
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

}
