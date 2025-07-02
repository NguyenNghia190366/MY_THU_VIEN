package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "MainController", urlPatterns = {"/", "/MainController"})
public class MainController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String web_url = "index.jsp";
        try {
            String action = request.getParameter("action");

            // Nếu không có action (truy cập trang chủ), load sách mới
            if (action == null) {
                request.setAttribute("action", "show");
                request.getRequestDispatcher("BookController").forward(request, response);
                return;
            }

            switch (action) {
                case "home":
                    request.setAttribute("action", "show");
                    request.getRequestDispatcher("BookController").forward(request, response);
                    return;

                case "login":
                    request.getRequestDispatcher("LoginController").forward(request, response);
                    break;
                case "Change":
                    web_url = "ShowSystemConfig";
                    break;
                case "bookDetail":
                    String bookID = request.getParameter("bookID");
                    if (bookID != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("BOOK", bookID);
                    }
                    web_url = "BookDetail.jsp";
                    break;
                case "save change this book":
                    web_url = "EditBookController";
                    break;
                case "approve":
                case "reject":
                case "borrowed":
                    web_url = "RequestChoiceController";
                    break;
                default:
                    request.setAttribute("msg", "Unknown action: " + action);
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "Unexpected error in MainController.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Central dispatcher for actions in the application";
    }
}
