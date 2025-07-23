package Controller;

import dao.BookDAO;
import dao.InventoryDAO;
import dto.Inventory;
import dto.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "InventoryController", urlPatterns = {"/InventoryController"})
public class InventoryController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            User us = (User) session.getAttribute("USER");

            if (us == null || !"admin".equalsIgnoreCase(us.getRole())) {
                session.setAttribute("ERROR_MESSAGE", "You must login as admin!");
                response.sendRedirect("Login.jsp");
                return;
            }

            String action = request.getParameter("action");
            if ("createInventory".equals(action)) {
                createInventory(request, response, us.getId());
            } else if ("viewHistory".equals(action)) {
                viewHistory(request, response);
            } else if ("updateStatus".equals(action)) {
                updateStatus(request, response);
            } else {
                request.setAttribute("MSG", "Unknown action!");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("MSG", "Unexpected error in InventoryController.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void createInventory(HttpServletRequest request, HttpServletResponse response, int adminID)
            throws ServletException, IOException {
        try {
            int bookID = Integer.parseInt(request.getParameter("bookID"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String expectedDate = request.getParameter("expected_date");
            String note = request.getParameter("note");

            InventoryDAO dao = new InventoryDAO();
            boolean check = dao.createInventoryOrder(bookID, adminID, quantity, expectedDate, note);

            if (check) {
                request.setAttribute("MSG", "Create order successfully!");
            } else {
                request.setAttribute("MSG", "Failed to create inventory order!");
            }

            // Sau khi tạo xong, load lại list books
            BookDAO bdao = new BookDAO();
            request.setAttribute("LIST_BOOKS", bdao.getAllBooks());

            request.getRequestDispatcher("AdminDashboard.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("MSG", "Error creating inventory order!");
            request.getRequestDispatcher("AdminDashboard.jsp").forward(request, response);
        }
    }

    private void viewHistory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            InventoryDAO dao = new InventoryDAO();
            ArrayList<Inventory> history = dao.getAllInventoryOrders();

            if (history == null || history.isEmpty()) {
                request.setAttribute("MSG", "No inventory history found!");
            } else {
                request.setAttribute("HISTORY_LIST", history);
            }

            request.getRequestDispatcher("HistoryUpdateInventory.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("MSG", "Error loading inventory history!");
            request.getRequestDispatcher("HistoryUpdateInventory.jsp").forward(request, response);
        }
    }

    private void updateStatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int inventoryId = Integer.parseInt(request.getParameter("inventory_id"));
            String status = request.getParameter("status");
            String note = request.getParameter("note");
            String actualAddDate = request.getParameter("actual_add_date");

            InventoryDAO dao = new InventoryDAO();
            boolean check = dao.updateStatus(inventoryId, status, note, actualAddDate);

            if (check) {
                request.setAttribute("MSG", "Status updated successfully!");
            } else {
                request.setAttribute("MSG", "Update failed!");
            }

            viewHistory(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("MSG", "Error updating status!");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Handles inventory operations";
    }
}
