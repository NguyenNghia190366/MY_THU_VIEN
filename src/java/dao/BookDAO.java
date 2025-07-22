/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.Book;
import java.sql.Connection;
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
public class BookDAO {

    public ArrayList<Book> viewBookList() {
        ArrayList<Book> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT  [id]\n"
                        + "      ,[title]\n"
                        + "      ,[author]\n"
                        + "      ,[isbn]\n"
                        + "      ,[category]\n"
                        + "      ,[published_year]\n"
                        + "      ,[total_copies]\n"
                        + "      ,[available_copies]\n"
                        + "      ,[status]\n"
                        + "      ,[url]\n"
                        + "  FROM [library_system].[dbo].[books]";
                Statement st = cn.createStatement();
                ResultSet table = st.executeQuery(sql);
                if (table != null) {
                    while (table.next()) {
                        int id = table.getInt("id");
                        String title = table.getString("title");
                        String author = table.getString("author");
                        String isbn = table.getString("isbn");
                        String category = table.getString("category");
                        int published_year = table.getInt("published_year");
                        int total_copies = table.getInt("total_copies");
                        int available_copies = table.getInt("available_copies");
                        String status = table.getString("status");
                        String url = table.getString("url");
                        Book book = new Book(id, title, author, isbn, category, published_year, total_copies, available_copies, status, url);
                        list.add(book);
                    }
                }
            }
        } catch (Exception e) {
        }
        return list;
    }

    //-----------------Hien sach moi nhat trong homepage----------------
    public ArrayList<Book> getNewestBooks(int limit) {
        ArrayList<Book> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT TOP (?) * FROM books ORDER BY published_year DESC";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, limit);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    Book book = new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("isbn"),
                            rs.getString("category"),
                            rs.getInt("published_year"),
                            rs.getInt("total_copies"),
                            rs.getInt("available_copies"),
                            rs.getString("status"),
                            rs.getString("url")
                    );
                    list.add(book);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

//----------------------------Search Books-----------------------------------
   public List<Book> searchBooks(String keyword) {
    List<Book> list = new ArrayList<>();
    try {
        String sql = "SELECT * FROM Books WHERE title LIKE ? OR author LIKE ? OR category LIKE ?";
        PreparedStatement ps = DBUtils.getConnection().prepareStatement(sql);
        String likeKeyword = "%" + keyword + "%";
        ps.setString(1, likeKeyword);
        ps.setString(2, likeKeyword);
        ps.setString(3, likeKeyword);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new Book(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getInt("available_copies"),
                rs.getString("url")
            ));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

    //------------------------ Lay Book theo ID ------------------------

       public Book getBookById(int id) {
        Book book = null;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT * FROM books WHERE id = ? AND status = 'active'";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        book = new Book();
                        book.setId(rs.getInt("id"));
                        book.setTitle(rs.getString("title"));
                        book.setAuthor(rs.getString("author"));
                        book.setIsbn(rs.getString("isbn"));
                        book.setCategory(rs.getString("category"));
                        book.setPublished_year(rs.getInt("published_year"));
                        book.setTotal_copies(rs.getInt("total_copies"));
                        book.setAvailable_copies(rs.getInt("available_copies"));
                        book.setStatus(rs.getString("status"));
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return book;
    }


    public Book findBookById(int id) {
        ArrayList<Book> list = this.viewBookList();
        Book book = null;
        for (int i = 0; i < list.size(); i++) {
            if (id == list.get(i).getId()) {
                book = list.get(i);
                //book.getStatus().equals(book)
            }
        }
        return book;
    }

    public void editBookInformation(Book book) {
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "UPDATE books SET TITLE=?, AUTHOR=?, isbn=?, category=?, published_year=?, total_copies=?,available_copies=?,status=?,url=? WHERE id=?";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setString(1, book.getTitle());
                st.setString(2, book.getAuthor());
                st.setString(3, book.getIsbn());
                st.setString(4, book.getCategory());
                st.setInt(5, book.getPublished_year());
                st.setInt(6, book.getTotal_copies());
                st.setInt(7, book.getAvailable_copies());
                st.setString(8, book.getStatus());
                st.setString(9, book.getUrl());
                st.setInt(10, book.getId());
                st.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
