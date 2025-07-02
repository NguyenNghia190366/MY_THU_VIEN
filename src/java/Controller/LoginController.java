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
        PrintWriter out = response.getWriter();
        String email = request.getParameter("txtemail");
        String password = request.getParameter("txtpassword");

        UserDAO d = new UserDAO();
        User us = d.checkUserExist(email, password);

        if (us != null) {
            //tinh nang: welcome, shopping cart, request borrow, change profile,...
            // luu us object vao session cua client vi can no cho cac tinh nang tiep theo
            HttpSession s = request.getSession();
            s.setAttribute("user", us);

            // Nếu có redirectBackTo (do bị chuyển hướng lúc chưa login), quay về lại đó
            String redirect = (String) s.getAttribute("redirectBackTo");
            if (redirect != null) {
                s.removeAttribute("redirectBackTo"); 
                response.sendRedirect(redirect);
                return;
            }

            // Redirect như thường khi login xong
            if (us.getRole().equalsIgnoreCase("admin")) {
                response.sendRedirect("ConfigController?action=show");
            } else if (us.getRole().equalsIgnoreCase("user")) {
                response.sendRedirect("UserDashboard.jsp");  // servlet --> them duoi jsp
            }
        } else {
            //day loi tu LoginController ve trang Login de in ra man hinh
            //muon day thi phai luu data trong upplication(?), session or request
            //redirect or dispatcher
            //chon dispatcher: request ben nay cung la request ben kia. in loi ngay tren form minh nhao
            request.setAttribute("ERROR", "Email or Password is invalid");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
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
