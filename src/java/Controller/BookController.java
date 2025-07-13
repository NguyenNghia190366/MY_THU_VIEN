package Controller;

import dao.BookDAO;
import dao.SystemConfigDAO;
import dto.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "BookController", urlPatterns = {"/BookController"})
public class BookController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        // Nếu không có action (truy cập trang chủ), redirect đến BookController
        if (action == null) {
            response.sendRedirect("BookController?action=show");
            return;
        }

        switch (action) {
            case "search":
                searchBook(request, response);
                break;
            case "borrow":
                borrowBook(request, response);
                break;
            case "remove":
                removeFromCart(request, response);
                break;
            case "showNew":
                showAllNewBook(request, response);
                break;
            case "showAvai":
                showAllAvAilableBook(request, response);
                break;
            default:
                // Nếu action không hợp lệ, redirect về trang chủ
                response.sendRedirect("BookController?action=showNew");
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

    private void searchBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String keyword = request.getParameter("txtsearch");
            String searchby = request.getParameter("searchby");

            if (keyword == null) {
                keyword = "";
            }
            if (searchby == null) {
                searchby = "";
            }

            BookDAO dao = new BookDAO();
            List<Book> list = dao.getBooks(keyword, searchby);

            request.setAttribute("BOOK_RESULT", list);

            //đưa cái keyword vào session để tí nó sẽ back lại với kết quả cũ
            HttpSession session = request.getSession();
            session.setAttribute("SEARCH_KEYWORD", keyword);
            session.setAttribute("SEARCH_BY", searchby);

            request.getRequestDispatcher("ViewBook.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "Error while updating config.");
            request.getRequestDispatcher("error.jsp").forward(request, response);

        }
    }

    private void borrowBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String returnUrl = request.getParameter("returnUrl");
            if (returnUrl == null || returnUrl.trim().isEmpty()) {
                returnUrl = "MainController?action=home";
            }

            HttpSession session = request.getSession();
            Object user = session.getAttribute("user");

            if (user == null) {
                // Guest --> Login.jsp (lưu nhớ url gốc r mới sang Login)
                session.setAttribute("redirectBackTo", returnUrl);
                response.sendRedirect("Login.jsp");
                return;
            }

            String id = request.getParameter("txtid");
            // Nếu đã đăng nhập → tiếp tục borrow -> thêm vào cart
            BookDAO dao = new BookDAO();
            Book book = dao.getBook(Integer.parseInt(id.trim()));

            if (book != null) {
                List<Book> cart = (List<Book>) session.getAttribute("CART");
                if (cart == null) {
                    cart = new ArrayList<>();
                }

                //check if that book already exist in cart
                boolean found = false;
                for (Book b : cart) {
                    if (b.getId() == book.getId()) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    cart.add(book);
                    session.setAttribute("CART", cart);
                }
            }

            response.sendRedirect(returnUrl);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "Error while updating config.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void removeFromCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String id = request.getParameter("id");
            HttpSession session = request.getSession();
            List<Book> cart = (List<Book>) session.getAttribute("CART");

            if (cart != null) {
                for (int i = 0; i < cart.size(); i++) {
                    if (cart.get(i).getId() == Integer.parseInt(id)) {
                        cart.remove(i);
                        break;
                    }
                }
                session.setAttribute("CART", cart);
            }

            response.sendRedirect("viewcart.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "Error while updating config.");
            request.getRequestDispatcher("error.jsp").forward(request, response);

        }
    }

    private void showAllNewBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //take config new_book_year_range to filter new book
            SystemConfigDAO configDAO = new SystemConfigDAO();
            int years = configDAO.getNewBookYears();
            System.out.println("Years for new books: " + years); // Debug log

            //List with all new book
            BookDAO bDAO = new BookDAO();
            ArrayList<Book> newBookList = bDAO.getAllNewBookByYear("", "", "", years);
            System.out.println("Found " + newBookList.size() + " new books"); // Debug log

            //Save into request scope for jsp use them
            request.setAttribute("LIST_NEW", newBookList);

            //Forward to guest home page (GuestHomePage.jsp)
            request.getRequestDispatcher("GuestHomePage.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "Error loading new books for home page: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void showAllAvAilableBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            BookDAO ab = new BookDAO();
            ArrayList<Book> abList = ab.getAvailableBook();
            System.out.println("Found " + abList.size() + " available books.");
            // debug log

            // Save into session scope then UserDashboard can use them
            request.setAttribute("LIST_AVAILABLE", abList);

            // Forward to UserDashboard to display 
            request.getRequestDispatcher("UserDashboard.jsp").forward(request, response);

            System.out.println(">>> DEBUG: Found " + abList.size() + " available books");
            for (Book b : abList) {
                System.out.println(">>> Book: " + b.getTitle());
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "Error from loading available books: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

    }
}
