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
        String web_url = "GuestHomePage.jsp";

        try {
            String action = request.getParameter("action");
            HttpSession session;
            String role;

            // Nếu không có action (truy cập trang chủ), load sách mới
            if (action == null) {
                web_url = "BookController?action=showNew";
            } else {
                switch (action) {
                    case "home":
                        session = request.getSession(true);
                        role = (session.getAttribute("ROLE") != null)
                                ? session.getAttribute("ROLE").toString()
                                : "guest";

                        if ("user".equalsIgnoreCase(role)) {
                            web_url = "BookController?action=showAvai";
                        } else if ("admin".equalsIgnoreCase(role)) {
                            web_url = "StatisticController";
                        } else {
                            web_url = "BookController?action=showNew";
                        }
                        break;

                    case "login":
                        web_url = "LoginController";
                        break;

                    case "search":
                        web_url = "BookController";
                        break;

                    case "Change":
                        web_url = "ShowSystemConfig";
                        break;

                    case "viewHistory":
                        web_url = "BorrowHistoryController";
                        break;

                    case "borrow":
                    case "return":
                        web_url = "BookRequestController";
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

                                session = request.getSession(true);
                                session.setAttribute("BOOK", bookID);

                                role = (session.getAttribute("ROLE") != null)
                                        ? session.getAttribute("ROLE").toString()
                                        : "guest";

                                if ("admin".equalsIgnoreCase(role)) {
                                    web_url = "BookDetail.jsp";
                                } else {
                                    web_url = "BookDetailUser.jsp";
                                }
                            } else {
                                request.setAttribute("msg", "Book ID is missing.");
                                web_url = "error.jsp";
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            request.setAttribute("msg", "Error loading book detail.");
                            web_url = "error.jsp";
                        }
                        break;

                    case "save change this book":
                        web_url = "EditBookController";
                        break;

                    case "approve":
                    case "reject":
                    case "borrowed":
                        web_url = "RequestChoiceController";
                        break;

                    case "show user":
                        web_url = "UserCheckController";
                        break;

                    case "disable":
                    case "enable":
                        request.setAttribute("CHANGE_STATUS", "true");
                        web_url = "UserCheckController";
                        break;

                    case "add book":
                    case "remove book":
                        web_url = "EditBookController";
                        break;

                    default:
                        request.setAttribute("msg", "Unknown action: " + action);
                        web_url = "error.jsp";
                        break;
                }
            }

            request.getRequestDispatcher(web_url).forward(request, response);

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
