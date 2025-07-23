<%-- 
    Document   : setupSystemConfig
    Created on : Jul 22, 2025, 4:27:34 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

    <c:if test="${empty sessionScope.USER}">
        <c:redirect url="Login.jsp"/>
    </c:if>

    <!-- Hiện thông báo nếu có -->
    <c:if test="${not empty sessionScope.ERROR_MESSAGE}">
        <p style="color:red;">${sessionScope.ERROR_MESSAGE}</p>
        <c:remove var="ERROR_MESSAGE" scope="session"/>
    </c:if>

    <h4>Welcome ${sessionScope.USER.name} comeback</h4>
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

    <c:if test="${not empty MSG}">
        <p style="color:red;">${MSG}</p>
    </c:if>

</body>
</html>
