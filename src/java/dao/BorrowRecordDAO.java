/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.Book;
import dto.BorrowRecord;
import dto.Fine;
import dto.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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

    public BorrowRecord findBorrowRecordByID(int id) {
        BorrowRecord record = null;
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
                        + "  FROM [library_system].[dbo].[borrow_records] WHERE [id] = ?;";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, id);
                ResultSet table = st.executeQuery();
                if (table != null && table.next()) {

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

                    record = new BorrowRecord(id, user, book, borrow_date, due_date, return_date, status);

                }
            }
        } catch (Exception e) {
        }
        return record;
    }

    public List<BorrowRecord> getBorrowHistoryByUserId(int userId) {
        List<BorrowRecord> records = new ArrayList<>();

        try ( Connection cn = DBUtils.getConnection()) {
            String sql = "SELECT br.*, b.id as book_id, b.title, b.author, b.isbn, b.category, b.published_year, b.total_copies, b.available_copies, b.status as book_status "
                    + "FROM borrow_records br "
                    + "JOIN books b ON br.book_id = b.id "
                    + "WHERE br.user_id = ? "
                    + "ORDER BY br.borrow_date DESC";

            PreparedStatement st = cn.prepareStatement(sql);
            st.setInt(1, userId);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                // Tạo Book
                Book book = new Book();
                book.setId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setIsbn(rs.getString("isbn"));
                book.setCategory(rs.getString("category"));
                book.setPublished_year(rs.getInt("published_year"));
                book.setTotal_copies(rs.getInt("total_copies"));
                book.setAvailable_copies(rs.getInt("available_copies"));
                book.setStatus(rs.getString("book_status"));

                // Tạo BorrowRecord
                BorrowRecord record = new BorrowRecord(
                        rs.getInt("id"),
                        null, // có thể thêm thông tin user nếu muốn
                        book,
                        rs.getDate("borrow_date"),
                        rs.getDate("due_date"),
                        rs.getDate("return_date"),
                        rs.getString("status")
                );

                records.add(record);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return records;
    }
}
