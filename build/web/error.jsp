<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head><title>Error</title></head>
<body>
    <h2>Oops! Something went wrong.</h2>
    <p>${requestScope.msg != null ? requestScope.msg : "An unexpected error occurred."}</p>
    <a href="GuestHomePage.jsp">Back to Home</a>
</body>
</html>
