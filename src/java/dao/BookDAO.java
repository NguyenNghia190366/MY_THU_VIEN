/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Year;
import java.util.ArrayList;
import mylib.DBUtils;

/**
 *
 * @author Admin
 */
public class BookDAO {

    public ArrayList<Book> getBooks(String keyword, String searchBy) {
        ArrayList<Book> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql;

                if (searchBy == null || searchBy.trim().isEmpty()) {
                    sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR category LIKE ?";
                } else if ("title".equalsIgnoreCase(searchBy)) {
                    sql = "SELECT * FROM books WHERE title LIKE ?";
                } else if ("author".equalsIgnoreCase(searchBy)) {
                    sql = "SELECT * FROM books WHERE author LIKE ?";
                } else if ("category".equalsIgnoreCase(searchBy)) {
                    sql = "SELECT * FROM books WHERE category LIKE ?";
                } else {
                    // default fallback
                    sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR category LIKE ?";
                }

                PreparedStatement st = cn.prepareStatement(sql);

                if (sql.contains("OR")) {
                    st.setString(1, "%" + keyword + "%");
                    st.setString(2, "%" + keyword + "%");
                    st.setString(3, "%" + keyword + "%");
                } else {
                    st.setString(1, "%" + keyword + "%");
                }

                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    // Tạo Book, add list
                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    String isbn = rs.getString("isbn");
                    String category = rs.getString("category");
                    int year = rs.getInt("published_year");
                    int total = rs.getInt("total_copies");
                    int available = rs.getInt("available_copies");
                    String status = rs.getString("status");
                    String url = rs.getString("url");

                    Book b = new Book(id, title, author, isbn, category, year, total, available, status, url);
                    list.add(b);
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
                e.printStackTrace();
            }
        }
        return list;
    }

    public Book getBook(int id) {
        Book b = null;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                // Sửa câu lệnh SQL để tìm theo id
                String sql = "select id,title,author,isbn,category,published_year,total_copies,available_copies,status,url\n"
                        + "from dbo.books\n"
                        + "where id = ?"; // Sửa thành điều kiện id

                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, id); // Set id vào tham số
                ResultSet table = st.executeQuery();

                if (table != null && table.next()) {
                    String title = table.getString("title");
                    String author = table.getString("author");
                    String isbn = table.getString("isbn");
                    String category = table.getString("category");
                    int year = table.getInt("published_year");
                    int totol_copy = table.getInt("total_copies");
                    int available_copy = table.getInt("available_copies");
                    String status = table.getString("status");
                    String url = table.getString("url");
                    b = new Book(id, title, author, isbn, category, year, totol_copy, available_copy, status, url);
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
                e.printStackTrace();
            }
        }
        return b;
    }

    public ArrayList<Book> getAvailableBook() {
        ArrayList<Book> availableList = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select [id], [title], [author], [isbn], [category], [published_year], [total_copies], \n"
                        + "	[available_copies], [status], [url]\n"
                        + "from [dbo].[books]\n"
                        + "where [available_copies] > 0";

                PreparedStatement pst = cn.prepareStatement(sql);

                ResultSet table = pst.executeQuery();

                while (table != null && table.next()) {
                    int id = table.getInt("id");
                    String title = table.getString("title");
                    String author = table.getString("author");
                    String isbn = table.getString("isbn");
                    String category = table.getString("category");
                    int year = table.getInt("published_year");
                    int totol_copy = table.getInt("total_copies");
                    int available_copy = table.getInt("available_copies");
                    String status = table.getString("status");
                    String url = table.getString("url");
                    Book b = new Book(id, title, author, isbn, category, year, totol_copy, available_copy, status, url);
                    availableList.add(b);
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
                e.printStackTrace();
            }
        }
        return availableList;
    }

    public ArrayList<Book> getAllNewBookByYear(String name, String au, String cate, int years) {
        ArrayList<Book> listNew = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                int currentYear = Year.now().getValue();
                int minYear = currentYear - years;

                // Câu truy vấn sửa lại dùng BETWEEN
                String sql = "SELECT id,title,author,isbn,category,published_year,total_copies,available_copies,status,url "
                        + "FROM books "
                        + "WHERE published_year BETWEEN ? AND ? "
                        + "AND (title LIKE ? OR author LIKE ? OR category LIKE ?)";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, minYear);
                pst.setInt(2, currentYear);
                pst.setString(3, "%" + name + "%");
                pst.setString(4, "%" + au + "%");
                pst.setString(5, "%" + cate + "%");

                ResultSet table = pst.executeQuery();
                while (table != null && table.next()) {
                    int id = table.getInt("id");
                    String title = table.getString("title");
                    String author = table.getString("author");
                    String isbn = table.getString("isbn");
                    String category = table.getString("category");
                    int year = table.getInt("published_year");
                    int total_copy = table.getInt("total_copies");
                    int available_copy = table.getInt("available_copies");
                    String status = table.getString("status");
                    String url = table.getString("url");

                    Book b = new Book(id, title, author, isbn, category, year, total_copy, available_copy, status, url);
                    listNew.add(b);
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
                e.printStackTrace();
            }
        }
        return listNew;
    }

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

    public Book findBookById(int id) {
        Book book = null;
        try ( Connection cn = DBUtils.getConnection()) {
            if (cn != null) {
                String sql = "SELECT * FROM books WHERE id = ?";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    int bookId = rs.getInt("id");
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    String isbn = rs.getString("isbn");
                    String category = rs.getString("category");
                    int year = rs.getInt("published_year");
                    int totalCopy = rs.getInt("total_copies");
                    int availableCopy = rs.getInt("available_copies");
                    String status = rs.getString("status");
                    String url = rs.getString("url");
                    book = new Book(bookId, title, author, isbn, category, year, totalCopy, availableCopy, status, url);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
