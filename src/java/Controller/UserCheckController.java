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
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
public class UserCheckController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if(request.getAttribute("CHANGE_STATUS") != null){
                int id = Integer.parseInt(request.getParameter("userID"));
                UserDAO d = new UserDAO();
                int re = d.changeStatusUser(id);
                if(re > 0){
                    request.setAttribute("SUCCESS", "The status of user was changed!!!");
                    request.getRequestDispatcher("ShowManageUser.jsp").forward(request, response);
                } else {
                    request.setAttribute("NOT_CHANGE", "The status of user was not changed!!!");
                    request.getRequestDispatcher("ShowManageUser.jsp").forward(request, response);
                }
                
            } else {
            String email = request.getParameter("emailtxt");
            UserDAO d = new UserDAO();
            User user = d.showUserByEmail(email);
            if(user!=null){
                if(user.getRole().equals("user")){
                request.setAttribute("USER", user);
                request.getRequestDispatcher("ShowManageUser.jsp").forward(request, response);
                } else if(user.getRole().equals("admin")){
                    request.setAttribute("ADMIN_EMAIL", "You just input the admin email");
                request.getRequestDispatcher("ShowManageUser.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("NOT_EXISTED_EMAIL", "No user had that email !!!");
                request.getRequestDispatcher("ShowManageUser.jsp").forward(request, response);
            }
        }
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
