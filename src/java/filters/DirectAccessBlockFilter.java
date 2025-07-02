package filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class DirectAccessBlockFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        // ✅ Bỏ qua những URL không cần chặn
        if (uri.endsWith("MainController")
                || uri.contains("BookController")
                || uri.endsWith(".css") || uri.endsWith(".js")
                || uri.endsWith(".jpg") || uri.endsWith(".png")
                || uri.endsWith("Login.jsp")
                || uri.endsWith("SignUp.html")
                || uri.endsWith("error.jsp")) {
            chain.doFilter(request, response);
            return;
        }

        // ✅ Nếu truy cập trực tiếp index.jsp thì chặn và redirect về MainController
        if (uri.endsWith("index.jsp")) {
            res.sendRedirect("MainController?action=home");
            return;
        }

        if (uri.endsWith("AdminDashboard.jsp")) {
            HttpSession session = req.getSession(false);

            if (session != null && session.getAttribute("user") != null) {
                Object role = ((dto.User) session.getAttribute("user")).getRole();
                if ("admin".equalsIgnoreCase(role.toString())) {
                    // Cho đi tiếp vì là admin --> xử lý ở servlet rồi
                    chain.doFilter(request, response);
                } else {
                    // Không phải admin => chuyển hướng về dashboard user và báo lỗi
                    session.setAttribute("msg", "❌ Bạn không có quyền truy cập trang Admin!");
                    res.sendRedirect("UserDashboard.jsp");
                }
            } else {
                // Chưa đăng nhập
                res.sendRedirect("Login.jsp");
            }
            return;
        }

        // ✅ Còn lại thì cho đi tiếp
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }
}
