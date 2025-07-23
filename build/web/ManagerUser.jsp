<%-- 
    Document   : ManageUser
    Created on : Jul 22, 2025, 3:05:34 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="dto.Book"%>
<%@page import="dto.BookRequest"%>
<%@page import="dao.BookRequestDAO"%>
<%@page import="dto.User"%>
<%@page import="dao.UserDAO"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="MainController" method="post">
            <input type="email" name="emailtxt" required="">
            <input type="submit" name="action" value="show user">
        </form>
        ${requestScope.ERROR}
        ${requestScope.ADMIN_EMAIL} 
        <%
User user = (User) request.getAttribute("USER");
if(user!=null){%>
        <table>
            <tr>
                <td>name: </td>
                <td><%=user.getName()%></td>
            </tr>
            <tr>
                <td>email: </td>
                <td><%=user.getEmail()%></td>
            </tr>
        </table>
        <%if(user.getStatus().equals("active")){%>
        <form action="MainController" method="post">
            <input type="hidden" name="userID" value="${user.id}">
            <input type="submit" name="action" value="disable">
        </form>
        <%} else if(user.getStatus().equals("blocked")){%>

        <form action="MainController" method="post">
            <input type="hidden" name="userID" value="${user.id}">
            <input type="submit" name="action" value="enable">
        </form>
        <%}%>

        <%}%>         

    </body>
</html>
