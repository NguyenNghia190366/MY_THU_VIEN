package dao;

import dto.Book;
import dto.MonthlyStat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mylib.DBUtils;

public class StatisticDAO {

    public int getTotalBooks() throws Exception {
        int total = 0;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT COUNT(*) FROM [dbo].[books]";
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return total;
    }

    public int getTotalUsers() throws Exception {
        int total = 0;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT COUNT(*) FROM [dbo].[users]";
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return total;
    }

    public int getCurrentlyBorrowed() throws Exception {
        int total = 0;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT COUNT(*) FROM [dbo].[borrow_records] WHERE [status] = 'borrowed'";
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return total;
    }

    public List<Book> getTopBorrowedBooks() throws Exception {
        List<Book> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT TOP 5 b.id, b.title, COUNT(*) AS borrow_count " +
                             "FROM borrow_records br " +
                             "JOIN books b ON br.book_id = b.id " +
                             "GROUP BY b.id, b.title " +
                             "ORDER BY borrow_count DESC";
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    Book b = new Book();
                    b.setId(rs.getInt("id"));
                    b.setTitle(rs.getString("title"));
                    b.setBorrowCount(rs.getInt("borrow_count"));
                    list.add(b);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<MonthlyStat> getMonthlyBorrowStats() throws Exception {
        List<MonthlyStat> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT MONTH(borrow_date) AS month, COUNT(*) AS borrow_count " +
                             "FROM borrow_records " +
                             "GROUP BY MONTH(borrow_date) " +
                             "ORDER BY month";
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    MonthlyStat stat = new MonthlyStat();
                    stat.setMonth(rs.getInt("month"));
                    stat.setBorrowCount(rs.getInt("borrow_count"));
                    list.add(stat);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public double getAvgBorrowDuration() throws Exception {
        double avg = 0;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT AVG(DATEDIFF(day, borrow_date, return_date)) AS avg_days " +
                             "FROM borrow_records WHERE return_date IS NOT NULL";
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    avg = rs.getDouble("avg_days");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return avg;
    }
}
