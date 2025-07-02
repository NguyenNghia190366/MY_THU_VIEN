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
            case "show":
                showAllNewBook(request, response);
                break;
            default:
                // Nếu action không hợp lệ, redirect về trang chủ
                response.sendRedirect("BookController?action=show");
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
            if (keyword == null) {
                keyword = "";
            }

            BookDAO dao = new BookDAO();
            List<Book> list = dao.getBooks(keyword);

            request.setAttribute("BOOK_RESULT", list);

            //đưa cái keyword vào session để tí nó sẽ back lại với kết quả cũ
            HttpSession session = request.getSession();
            session.setAttribute("SEARCH_KEYWORD", keyword);

            request.getRequestDispatcher("ViewSearchBook.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "Error while updating config.");
            request.getRequestDispatcher("error.jsp").forward(request, response);

        }
    }

    private void borrowBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Object user = session.getAttribute("user");

            String id = request.getParameter("txtid");
            String keyword = request.getParameter("txtsearch");
            if (keyword == null) {
                keyword = "";
            }

            // Nếu chưa đăng nhập, redirect đến login
            if (user == null) {
                // Ghi nhớ URL để quay lại sau login
                String currentURL = "BookController?action=borrow&txtid=" + URLEncoder.encode(id, "UTF-8")
                        + "&txtsearch=" + URLEncoder.encode(keyword, "UTF-8");
                session.setAttribute("redirectBackTo", currentURL);

                // Chuyển hướng đến trang login
                response.sendRedirect("Login.jsp");
                return;
            }

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

            response.sendRedirect("BookController?action=search&txtsearch=" + URLEncoder.encode(keyword, "UTF-8"));
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
}
