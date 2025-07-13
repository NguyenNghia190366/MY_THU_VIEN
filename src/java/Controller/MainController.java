package Controller;

import dao.BookDAO;
import dto.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
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
            HttpSession session = null;
            String role = "";
            System.out.println(">>> ROLE: " + role);


            // Nếu không có action (truy cập trang chủ), load sách mới sẵn 
            if (action == null) {
                request.setAttribute("action", "showNew");
                request.getRequestDispatcher("BookController").forward(request, response);
                return;
            }

            switch (action) {
                case "home":
                    session = request.getSession(true);
                    role = (session.getAttribute("ROLE") != null)
                            ? session.getAttribute("ROLE").toString()
                            : "guest";

                    if ("user".equalsIgnoreCase(role)) {
                        request.getRequestDispatcher("BookController?action=showAvai").forward(request, response);
                    } else {
                        request.getRequestDispatcher("BookController?action=showNew").forward(request, response);
                    }
                    return;

                case "login":
                    request.getRequestDispatcher("LoginController").forward(request, response);
                    break;

                case "Change":
                    web_url = "ShowSystemConfig";
                    break;

                case "bookDetail":
                    try {
                    String bookID = request.getParameter("bookID");
                    if (bookID != null) {
                        BookDAO dao = new BookDAO();
                        Book book = dao.findBookById(Integer.parseInt(bookID));
                        System.out.println("Book ID: " + bookID);
                        System.out.println("Book found: " + book);
                        request.setAttribute("BOOK_DETAIL", book);
                        Cookie lastCategory = new Cookie("lastCategory", book.getCategory());
                        lastCategory.setMaxAge(60 * 60 * 24); // 1 ngày
                        response.addCookie(lastCategory);

                        session = request.getSession(false);
                        // Lấy ROLE từ session nếu có, không thì gán mặc định là "guest"
                        role = (session != null && session.getAttribute("ROLE") != null)
                                ? session.getAttribute("ROLE").toString() : "guest";

                        if ("admin".equalsIgnoreCase(role)) {
                            request.getRequestDispatcher("BookDetail.jsp").forward(request, response);
                        } else {
                            request.getRequestDispatcher("BookDetailUser.jsp").forward(request, response);
                        }
                        return;
                    } else {
                        request.setAttribute("msg", "Book ID is missing.");
                        request.getRequestDispatcher("error.jsp").forward(request, response);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("msg", "Error loading book detail.");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    return;
                }

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
