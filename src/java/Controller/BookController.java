package Controller;

import dao.BookDAO;
import dao.SystemConfigDAO;
import dto.Book;
import dto.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "BookController", urlPatterns = {"/BookController"})
public class BookController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

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
            case "loadMoreNew":
                showMoreBooksGeneric(request, response, "LIST_NEW", "new");
                break;
//            case "showAll":
//                showAllBook(request, response);
//                break;

            case "loadMoreAvailable":
                showMoreBooksGeneric(request, response, "LIST_AVAILABLE", "available");
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

            if (list == null) {
                request.setAttribute("MSG", "No book, author or category is match it.");
                request.getRequestDispatcher("ViewBook.jsp").forward(request, response);
            } else {

                request.setAttribute("BOOK_RESULT", list);

                //đưa cái keyword vào session để tí nó sẽ back lại với kết quả cũ
                HttpSession session = request.getSession();
                session.setAttribute("SEARCH_KEYWORD", keyword);
                session.setAttribute("SEARCH_BY", searchby);

                request.getRequestDispatcher("ViewBook.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("MSG", "Error while updating config.");
            request.getRequestDispatcher("error.jsp").forward(request, response);

        }
    }

    private void borrowBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String id = request.getParameter("txtid");
            String source = request.getParameter("source"); // mới: lấy source

            String returnUrl = request.getParameter("returnUrl");

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("USER");

            // Nếu chưa login
            if (user == null) {
                String borrowUrl = "BookController?action=borrow&txtid=" + id
                        + "&source=" + source
                        + "&returnUrl=" + java.net.URLEncoder.encode(returnUrl != null ? returnUrl : "", "UTF-8");
                response.sendRedirect("Login.jsp?REDIRECT_BACK_TO=" + java.net.URLEncoder.encode(borrowUrl, "UTF-8"));
                return;
            }

            // Đã login ➜ thêm sách vô giỏ
            BookDAO dao = new BookDAO();
            Book book = dao.getBook(Integer.parseInt(id.trim()));

            if (book != null) {
                List<Book> cart = (List<Book>) session.getAttribute("CART");
                if (cart == null) {
                    cart = new ArrayList<>();
                }

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

            // ➜ Xác định trang redirect sau khi borrow
            if ("guestHome".equals(source)) {
                response.sendRedirect("MainController?action=home");
            } else {
                // fallback nếu không rõ ➜ quay lại returnUrl hoặc GuestHomePage.jsp
                if (returnUrl == null || returnUrl.isEmpty()) {
                    returnUrl = "GuestHomePage.jsp";
                }
                response.sendRedirect(returnUrl);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("MSG", "Error while borrowing.");
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
            request.setAttribute("MSG", "Error while updating config.");
            request.getRequestDispatcher("error.jsp").forward(request, response);

        }
    }

    private void showAllNewBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            SystemConfigDAO configDAO = new SystemConfigDAO();
            int years = configDAO.getNewBookYears();
            System.out.println("Years for new books: " + years);

            BookDAO bDAO = new BookDAO();
            int page = 1; // trang đầu tiên
            int pageSize = 8; //cong them 8 cuon de hien thi

            ArrayList<Book> newBookList = bDAO.getBooksPaging(page, pageSize);

            System.out.println("Found " + newBookList.size() + " new books");

            // Lưu vào session để giữ list giữa các request
            HttpSession session = request.getSession();
            session.setAttribute("LIST_NEW", newBookList);

            // Cập nhật trang hiện tại
            request.setAttribute("LIST_NEW", newBookList);
            // Set cho page = 1 der khi qua nhan more ben jsp 
            //cai current_page dc cong 1 va hien thi them 8 cuon sach nua
            request.setAttribute("CURRENT_PAGE", page);

            request.getRequestDispatcher("GuestHomePage.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("MSG", "Error loading new books for home page: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void showAllAvAilableBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            BookDAO bdao = new BookDAO();
            int page = 1;
            int pageSize = 8;

            ArrayList<Book> avaiBookList = bdao.getAvailableBooksPaging(page, pageSize);

            // Luu vao sesion de giu dc list giua cac request 
            HttpSession session = request.getSession();
            session.setAttribute("LIST_AVAILABLE", avaiBookList);

            // Cap nhat trang hien tai
            request.setAttribute("LIST_AVAILABLE", avaiBookList);
            request.setAttribute("CURRENT_PAGE", page);

            request.getRequestDispatcher("UserDashboard.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("MSG", "Error from loading available books: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

    }

    private void showMoreBooksGeneric(HttpServletRequest request, HttpServletResponse response, String listName, String filterType)
            throws ServletException, IOException {
        try {
            String pageParam = request.getParameter("page");
            int page = 1;
            if (pageParam != null) {
                page = Integer.parseInt(pageParam);
            }

            // Mac dinh hien thi 8 cuon sach
            int pageSize = 8;

            BookDAO dao = new BookDAO();
            ArrayList<Book> moreBooks;

            if (filterType.equals("new")) {
                moreBooks = dao.getBooksPaging(page, pageSize);
            } else if (filterType.equals("available")) {
                moreBooks = dao.getAvailableBooksPaging(page, pageSize);
            } else {
                throw new IllegalArgumentException("Unknown filter type: " + filterType);
            }

            HttpSession session = request.getSession();
            ArrayList<Book> currentList = (ArrayList<Book>) session.getAttribute(listName);
            if (currentList == null) {
                currentList = new ArrayList<>();
            }

            currentList.addAll(moreBooks);
            session.setAttribute(listName, currentList);
            request.setAttribute(listName, currentList);
            request.setAttribute("CURRENT_PAGE", page);

            // Dieu huong
            if (filterType.equals("new")) {
                request.getRequestDispatcher("GuestHomePage.jsp").forward(request, response);
            } else if (filterType.equals("available")) {
                request.getRequestDispatcher("UserDashboard.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("MSG", "Error loading more books: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

    }

    private void showAllBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String keyword = request.getParameter("keyword");
            BookDAO dao = new BookDAO();
            ArrayList<Book> books;

            if (keyword == null || keyword.trim().isEmpty()) {
                // 👉 Không có keyword thì lấy hết
                books = dao.getAllBooks();
            } else {
                // 👉 Có keyword thì search
                books = dao.searchBooksForAdmin(keyword);
            }

            if (books == null || books.isEmpty()) {
                request.setAttribute("MSG", "No book found.");
            } else {
                request.setAttribute("LIST_BOOKS", books);
            }

            request.getRequestDispatcher("AdminDashboard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
