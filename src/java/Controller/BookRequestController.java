package Controller;


import dao.BookDAO;
import dao.BookRequestDAO;
import dto.Book;
import dto.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "BookRequestController", urlPatterns = {"/BookRequestController"})
public class BookRequestController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy action: borrow hoặc return
            String action = request.getParameter("action");
            String idRaw = request.getParameter("id");

            // Kiểm tra hợp lệ ID
            if (idRaw == null || idRaw.trim().isEmpty()) {
                response.getWriter().println("Book ID is missing.");
                return;
            }

            int bookId = Integer.parseInt(idRaw);

            // Lấy thông tin sách để xác nhận
            BookDAO bookDAO = new BookDAO();
            Book book = bookDAO.getBookById(bookId);
            if (book == null) {
                response.getWriter().println("Book not found or invalid ID.");
                return;
            }

            // Lấy thông tin user từ session
            HttpSession session = request.getSession(false);
            User user = (session != null) ? (User) session.getAttribute("USER") : null;
            if (user == null) {
                response.sendRedirect("Login.jsp");
                return;
            }

            int userId = user.getId();
            String today = LocalDate.now().toString();

            // Xử lý yêu cầu
            BookRequestDAO requestDAO = new BookRequestDAO();
            if ("borrow".equalsIgnoreCase(action)) {
                requestDAO.requestToBorrow(userId, bookId, today);
            } else if ("return".equalsIgnoreCase(action)) {
                requestDAO.requestToReturn(userId, bookId, today);
            }

            // Redirect về chi tiết sách sau khi xử lý
            response.sendRedirect("ViewBookDetailController?id=" + bookId);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("An unexpected error occurred: " + e.getMessage());
        }
    }
}
