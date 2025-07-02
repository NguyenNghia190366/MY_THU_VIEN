package Controller;

import dao.SystemConfigDAO;
import dto.SystemConfig;
import dto.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ConfigController", urlPatterns = {"/ConfigController"})
public class ConfigController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User us = (User) session.getAttribute("user");

        // ✅ Kiểm tra quyền truy cập
        if (us == null || !"admin".equalsIgnoreCase(us.getRole())) {
            session.setAttribute("redirectBackTo", "ConfigController?action=show");
            response.sendRedirect("Login.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "show";
        }

        switch (action) {
            case "show":
                showConfigPage(request, response);
                break;

            default:
                if (action.startsWith("update_stock")) {
                    handleUpdate(request, response);
                } else {
                    response.sendRedirect("ConfigController?action=show");
                }
                break;
        }
    }

    private void showConfigPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SystemConfigDAO dao = new SystemConfigDAO();
        request.setAttribute("CONFIG_LIST", dao.getConfigList());
        request.getRequestDispatcher("AdminDashboard.jsp").forward(request, response);
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String key = action.substring("update_stock".length());
        String newValueRaw = request.getParameter("value_" + key);

        SystemConfigDAO dao = new SystemConfigDAO();
        try {
            double newValue = Double.parseDouble(newValueRaw);
            dao.updateConfigValue(key, newValue);
            request.setAttribute("msg", "Update successfully for: " + key);
        } catch (NumberFormatException e) {
            request.setAttribute("msg", "Invalid value for: " + key);
        }

        request.setAttribute("CONFIG_LIST", dao.getConfigList());
        request.getRequestDispatcher("AdminDashboard.jsp").forward(request, response);
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
}
