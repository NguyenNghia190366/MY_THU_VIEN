<%-- 
    Document   : ViewProfile
    Created on : Jun 3, 2025, 10:18:04 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page import = "dto.User" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <!-- If user chưa login thì redirect -->
        <c:if test="${empty sessionScope.USER}">
            <c:redirect url="Login.jsp"/>
        </c:if>
        
        <a href="UserDashboard.jsp">Back to Home</a>

        <c:if test="${not empty sessionScope.USER}">
            <h1>Profile</h1>
            <form action="ChangeProfileController" method="post">
                <p>Name: <input type="text" name = "txtname" value = "${sessionScope.USER.name}" required=""/>*</p>
                <p>Email: <input type="text" value = "${sessionScope.USER.email}" readonly=""/></p>
                <p>Password: <input type="password" name = "txtpassword" value = "${sessionScope.USER.password}" required=""/>*</p>
                <p>Role: <input type="text" value = "${sessionScope.USER.role}" readonly=""/></p>
                <p>Status: <input type="text" value = "${sessionScope.USER.status}" readonly=""/></p>
                <p><input type="submit" value = "save"/></p>
            </form>
        </c:if>
    </body>
</html>
