/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.Inventory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import mylib.DBUtils;

/**
 *
 * @author DELL
 */
public class InventoryDAO {

    public boolean createInventoryOrder(int bookID, int adminID, int quantity, String expectedDate, String note) {
        Connection cn = null;
        boolean check = false;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "INSERT INTO inventories (book_id, admin_id, created_date, "
                        + "expected_add_date, quantity, status, note) "
                        + "VALUES (?, ?, GETDATE(), ?, ?, 'pending', ?)";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, bookID);
                pst.setInt(2, adminID);
                pst.setString(3, expectedDate);
                pst.setInt(4, quantity);
                pst.setString(5, note);

                check = pst.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
            }
        }
        return check;
    }

    public ArrayList<Inventory> getAllInventoryOrders() {
        ArrayList<Inventory> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT \n"
                        + "    i.[inventory_id],\n"
                        + "    i.[book_id],\n"
                        + "    b.[title] AS book_title,\n"
                        + "    i.[admin_id],\n"
                        + "    u.[name] AS admin_name,\n"
                        + "    i.[created_date],\n"
                        + "    i.[expected_add_date],\n"
                        + "    i.[actual_add_date],\n"
                        + "    i.[quantity],\n"
                        + "    i.[status],\n"
                        + "    i.[note]\n"
                        + "FROM [dbo].[inventories] i\n"
                        + "JOIN [dbo].[users] u ON i.admin_id = u.id\n"
                        + "JOIN [dbo].[books] b ON i.book_id = b.id\n"
                        + "ORDER BY i.[created_date] DESC;";
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    int invenID = rs.getInt("inventory_id");
                    int bookId = rs.getInt("book_id");
                    String title = rs.getString("book_title");
                    int adminId = rs.getInt("admin_id");
                    String adminName = rs.getString("admin_name");
                    String cd = rs.getString("created_date");
                    String ead = rs.getString("expected_add_date");
                    String aad = rs.getString("actual_add_date");
                    int quantity = rs.getInt("quantity");
                    String status = rs.getString("status");
                    String note = rs.getString("note");

                    Inventory inv = new Inventory(invenID, bookId, title, adminId, adminName, cd, ead, aad, quantity, status, note);

                    list.add(inv);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
            }
        }
        return list;
    }

    public boolean updateStatus(int inventoryId, String status, String note, String actualAddDate) {
        Connection cn = null;
        PreparedStatement pst = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "UPDATE inventories SET status = ?, note = ?, actual_add_date = ? WHERE inventory_id = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, status);
                pst.setString(2, note);
                pst.setString(3, actualAddDate);
                pst.setInt(4, inventoryId);
                int rows = pst.executeUpdate();

                // Nếu update thành công và completed thì cộng số lượng
                if (rows > 0 && "completed".equalsIgnoreCase(status)) {
                    String sql2 = "UPDATE books "
                            + "SET total_copies = total_copies + i.quantity, "
                            + "available_copies = available_copies + i.quantity "
                            + "FROM books b JOIN inventories i ON b.id = i.book_id "
                            + "WHERE i.inventory_id = ?";
                    pst = cn.prepareStatement(sql2);
                    pst.setInt(1, inventoryId);
                    pst.executeUpdate();
                }

                return rows > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
            }
        }
        return false;
    }

}
