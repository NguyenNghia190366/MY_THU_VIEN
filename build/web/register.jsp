<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Registration</title>
</head>
<body>
    <h2>Register</h2>
    <form action="RegisterController" method="post">
        <label>Name:</label><br/>
        <input type="text" name="name" required/><br/>

        <label>Email:</label><br/>
        <input type="email" name="email" required/><br/>

        <label>Password:</label><br/>
        <input type="password" name="password" required/><br/>

        <input type="submit" value="Register"/>
    </form>

    <p style="color:red">${error}</p>
</body>
</html>
