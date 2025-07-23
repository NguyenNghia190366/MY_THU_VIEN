/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import dao.UserDAO;
import dto.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author DELL
 */
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        try {
            String email = request.getParameter("txtemail");
            String password = request.getParameter("txtpassword");

            if (email == null || password == null) {
                request.setAttribute("ERROR_MESSAGE", "Email or Password is invalid");
                request.getRequestDispatcher("Login.jsp").forward(request, response);
                return;
            }

            UserDAO d = new UserDAO();
            User us = d.checkUserExist(email, password);

            if (us != null) {
                if(us.getStatus().equals("blocked")){
                    request.setAttribute("ERROR", "Your account was blocked. You can't not log in");
                request.getRequestDispatcher("Login.jsp").forward(request, response);
                } else {
               
                HttpSession session = request.getSession();
                session.setAttribute("USER", us);
                session.setAttribute("ROLE", us.getRole());

                // Lấy redirectBackTo từ PARAMETER chứ không phải session!
                String redirect = request.getParameter("REDIRECT_BACK_TO");
                if (redirect != null && !redirect.trim().isEmpty()) {
                    response.sendRedirect(redirect);
                    return;
                }

                // Bình thường nếu không có redirect thì xử lý tiếp
                if (us.getRole().equalsIgnoreCase("admin")) {
                    response.sendRedirect("MainController?action=home");
                } else if (us.getRole().equalsIgnoreCase("user")) {
                    response.sendRedirect("BookController?action=showAvai");
                }}
            } else {
                request.setAttribute("ERROR_MESSAGE", "Email or Password is invalid");
                request.getRequestDispatcher("Login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("ERROR", "Have an error when do login");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
