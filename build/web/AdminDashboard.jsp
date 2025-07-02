<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="dto.User"%>
<%
    User u = (User) session.getAttribute("user");
    if (u == null || !"admin".equalsIgnoreCase(u.getRole())) {
        session.setAttribute("redirectBackTo", "AdminDashboard.jsp");
        response.sendRedirect("Login.jsp");
        return;
    }

    if (request.getAttribute("CONFIG_LIST") == null) {
        response.sendRedirect("ConfigController?action=show");
        return;
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Admin Dashboard</title>
    </head>
    <body>
        <h4>Welcome ${sessionScope.user.name} comeback</h4>
        <p><a href="LogoutController">Logout</a></p>
        <p><a href="ViewProfile.jsp">Change Profile</a></p>

        <h3>System Configuration</h3>
        <form action="ConfigController" method="post">
            <table border="1" cellpadding="5">
                <tr>
                    <th>Key</th>
                    <th>Value</th>
                    <th>Description</th>
                    <th>Update</th>
                </tr>
                <c:forEach var="cfg" items="${CONFIG_LIST}">
                    <tr>
                        <td>${cfg.config_key}</td>
                        <td><input type="text" name="value_${cfg.config_key}" value="${cfg.config_value}" /></td>
                        <td>${cfg.description}</td>
                        <td><button type="submit" name="action" value="update_stock${cfg.config_key}">Save</button></td>
                    </tr>
                </c:forEach>
            </table>
        </form>

        <c:if test="${not empty msg}">
            <p style="color:red;">${msg}</p>
        </c:if>
    </body>
</html>
