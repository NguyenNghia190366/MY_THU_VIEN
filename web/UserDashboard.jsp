<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>User Dashboard</title>
    </head>
    <body>

        <!-- Nếu chưa đăng nhập thì về lại trang chủ -->
        <c:if test="${empty sessionScope.user}">
            <c:redirect url="index.jsp"/>
        </c:if>

        <!-- Nếu đã đăng nhập -->
        <c:if test="${not empty sessionScope.user}">
            <h4>Welcome ${sessionScope.user.name} comeback</h4>
            <p><a href="LogoutController">Logout</a></p>
            <p><a href="ViewProfile.jsp">Change Profile</a></p>
        </c:if>

        <!-- Hiện thông báo nếu có -->
        <c:if test="${not empty sessionScope.msg}">
            <p style="color:red;">${sessionScope.msg}</p>
            <c:remove var="msg" scope="session"/>
        </c:if>

    </body>
</html>
