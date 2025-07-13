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

        //Luôn đảm bảo ROLE tồn tại trước
        HttpSession session = req.getSession(true);
        if (session.getAttribute("ROLE") == null) {
            if (session.getAttribute("user") != null) {
                session.setAttribute("ROLE", "user");
            } else {
                session.setAttribute("ROLE", "guest");
            }
        }

        // Bỏ qua những URL không cần chặn
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
        if (uri.endsWith("GuestHomePage.jsp")) {
            res.sendRedirect("MainController?action=home");
            return;
        }

        // Nếu truy cập AdminDashboard.jsp thì chặn nếu không phải admin
        if (uri.endsWith("AdminDashboard.jsp")) {
            if (session.getAttribute("user") != null) {
                Object role = ((dto.User) session.getAttribute("user")).getRole();
                if ("admin".equalsIgnoreCase(role.toString())) {
                    chain.doFilter(request, response);
                } else {
                    session.setAttribute("msg", "You do not have access to the Admin page!");
                    res.sendRedirect("UserDashboard.jsp");
                }
            } else {
                session.setAttribute("msg", "Please login first!");
                res.sendRedirect("Login.jsp");
            }
            return;
        }
        
        // Nếu truy cập UserDashboard.jsp thì chặn nếu kh phải user 
        if(uri.endsWith("UserDashboard.jsp")){
            if(session.getAttribute("user") != null){
                Object role = ((dto.User) session.getAttribute("user")).getRole();
                if("user".equalsIgnoreCase(role.toString())){
                    chain.doFilter(request, response);
                }else if("admin".equalsIgnoreCase(role.toString())){
                    session.setAttribute("msg", "You are admin, you do not have access to the User page!");
                    res.sendRedirect("AdminDashboard.jsp");
                } else{
                    session.setAttribute("msg", "Please login first!");
                    res.sendRedirect("Login.jsp");
                }
            }
            return;
        }

        // Còn lại thì cho đi tiếp
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }
}
