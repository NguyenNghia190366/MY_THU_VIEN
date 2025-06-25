/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
public class MainController extends HttpServlet {
   
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
            /* TODO output your page here. You may use following sample code. */
            String web_url = "Homepage.jsp";
            try {
            
            String source = request.getParameter("action");
            if(source!=null){
                switch(source){
                    case "home":{
                        web_url = "Homepage.jsp";
                        break;
                    }
                    case "login":{
                        web_url = "LoginController";
                        break;
                    }
                      case "Change":{
                        web_url = "ShowSystemConfig";
                        break;
                }
                case "bookDetail":{
                    HttpSession session = request.getSession();
                    if(session.getAttribute("BOOK")!=null){
                        session.removeAttribute("BOOK");
                    session.setAttribute("BOOK", request.getParameter("bookID"));}
                    else{
                        session.setAttribute("BOOK", request.getParameter("bookID"));}

                    
                    web_url = "BookDetail.jsp";
                    break;
                    }
                case "save change this book":{
                    web_url = "EditBookController";
                    break;
                }
                case "approve":{
                        web_url = "RequestChoiceController";
                    }
                case "reject":{
                        web_url = "RequestChoiceController";
                    }
                case "borrowed":{
                        web_url = "RequestChoiceController";
                    }
       /*             case "home":{
                        web_url = "HomePage.jsp";
                    }
                    case "home":{
                        web_url = "HomePage.jsp";
                    }
                    case "home":{
                        web_url = "HomePage.jsp";
                    }
*/                    
                }
            }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    request.getRequestDispatcher(web_url).forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
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
