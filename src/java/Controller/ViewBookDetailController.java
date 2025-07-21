package Controller;

import dao.BookDAO;
import dto.Book;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name="ViewBookDetailController", urlPatterns={"/ViewBookDetailController"})
public class ViewBookDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            // Lấy id từ query parameter
            int bookId = Integer.parseInt(request.getParameter("id"));

            // Gọi DAO để lấy Book từ CSDL
            Book book = new BookDAO().getBookById(bookId);

            // Truyền dữ liệu qua JSP
            request.setAttribute("book", book);

            // Forward đến trang chi tiết
            request.getRequestDispatcher("BookDetail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            // Nếu id không hợp lệ
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid book ID");
        } catch (Exception e) {
            // Lỗi server
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error");
        }
    }
}
