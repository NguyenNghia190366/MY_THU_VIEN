<%-- 
    Document   : UserDashboard
    Created on : Jun 13, 2025, 11:04:32 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="dto.User" %>
<%
    
    if (session == null || session.getAttribute("USER") == null) {
        response.sendRedirect("Login.jsp");
        return;
    }
%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Dashboard</title>
    </head>
    <body>
        <h1>Hello</h1>
        <a href="MainController?action=logout"> <button type="button">Logout</button></a>
        <a href="MainController?action=changeProfile"> <button type="button">Change profile</button></a>
    </body>
</html>
