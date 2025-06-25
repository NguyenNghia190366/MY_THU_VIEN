/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.Book;
import dto.BorrowRecord;
import dto.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import mylib.DBUtils;

/**
 *
 * @author Admin
 */
public class BorrowRecordDAO {

    public int setOverdue() {
        int result = 0;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "UPDATE borrow_records\n"
                        + "SET status = 'overdue'\n"
                        + "WHERE due_date < Convert(date, GETDATE()) AND return_date IS NULL;";
                Statement st = cn.createStatement();
                result = st.executeUpdate(sql);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public ArrayList<BorrowRecord> showBorrowRecord() {
        ArrayList<BorrowRecord> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT [id]\n"
                        + "      ,[user_id]\n"
                        + "      ,[book_id]\n"
                        + "      ,[borrow_date]\n"
                        + "      ,[due_date]\n"
                        + "      ,[return_date]\n"
                        + "      ,[status]\n"
                        + "  FROM [library_system].[dbo].[borrow_records]";
                Statement st = cn.createStatement();
                ResultSet table = st.executeQuery(sql);
                if (table != null) {
                    while (table.next()) {
                        int id = table.getInt("id");
                        int user_id = table.getInt("user_id");
                        int book_id = table.getInt("book_id");
                        UserDAO ud = new UserDAO();
                        BookDAO bd = new BookDAO();
                        User user = ud.checkUserByID(user_id);
                        Book book = bd.findBookById(book_id);
                        Date borrow_date = table.getDate("borrow_date");
                        Date due_date = table.getDate("due_date");
                        Date return_date = table.getDate("return_date");
                        String status = table.getString("status");
                        BorrowRecord borrowRecord = new BorrowRecord(id, user, book, borrow_date, due_date, return_date, status);
                        list.add(borrowRecord);
                    }
                }
            }
        } catch (Exception e) {
        }
        return list;
    }
}
