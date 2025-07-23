/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import dao.BookDAO;
import dao.StatisticDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author DELL
 */
public class StatisticController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            StatisticDAO dao = new StatisticDAO();
            BookDAO bookDAO = new BookDAO(); // thêm cái này

            // Gọi DAO thống kê
            request.setAttribute("TOTAL_BOOKS", dao.getTotalBooks());
            request.setAttribute("TOTAL_USERS", dao.getTotalUsers());
            request.setAttribute("CURRENTLY_BORROWED", dao.getCurrentlyBorrowed());
            request.setAttribute("TOP_BORROWED_BOOKS", dao.getTopBorrowedBooks());
            request.setAttribute("MONTHLY_STATS", dao.getMonthlyBorrowStats());
            request.setAttribute("AVG_BORROW_DURATION", dao.getAvgBorrowDuration());

            // Gọi DAO lấy LIST_BOOKS
            request.setAttribute("LIST_BOOKS", bookDAO.getAllBooks()); // hoặc getAllBooksAdmin()

            // Forward đến Dashboard
            request.getRequestDispatcher("AdminDashboard.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("MSG", "Error loading statistics!");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
