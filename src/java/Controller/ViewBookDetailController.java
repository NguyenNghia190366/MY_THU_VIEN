package Controller;

import dao.BookDAO;
import dto.Book;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ViewBookDetailController", urlPatterns = {"/ViewBookDetailController"})
public class ViewBookDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                request.setAttribute("error", "Missing book ID.");
                request.getRequestDispatcher("BookDetail.jsp").forward(request, response);
                return;
            }

            int id = Integer.parseInt(idParam);
            BookDAO dao = new BookDAO();
            Book book = dao.getBookById(id);

            if (book != null) {
                request.setAttribute("book", book);
                request.getRequestDispatcher("BookDetail.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Book not found.");
                request.getRequestDispatcher("BookDetail.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Something went wrong while loading book details.");
            request.getRequestDispatcher("BookDetail.jsp").forward(request, response);
        }
    }
}
