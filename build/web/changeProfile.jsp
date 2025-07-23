<%@ page import="dto.User" %>
<%@ page session="true" %>
<%
    User user = (User) session.getAttribute("USER");
    if (user == null) {
        response.sendRedirect("Login.jsp");
        return;
    }
%>

<html>
<head><title>Change Profile</title></head>
<body>
    <h2>Update Your Profile</h2>
    <form action="MainController" method="post">
        <input type="hidden" name="action" value="changeProfile" />
        Full Name: <input type="text" name="name" value="<%=user.getName()%>" required /><br>
        Email: <input type="email" name="email" value="<%=user.getEmail()%>" required /><br>
        Password: <input type="text" name="password" value="<%=user.getPassword()%>" /><br>
        <input type="submit" value="Update Profile" />
    </form>

    <% if (request.getAttribute("message") != null) { %>
        <p style="color:green;"><%= request.getAttribute("message") %></p>
    <% } else if (request.getAttribute("error") != null) { %>
        <p style="color:red;"><%= request.getAttribute("error") %></p>
    <% } %>

    <a href="MainController?action=home">Back to Home</a>
</body>
</html>
