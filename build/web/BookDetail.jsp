<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dto.Book"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Book Detail</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                padding: 30px;
                background-color: #f9f9f9;
            }
            h2 {
                margin-bottom: 20px;
            }
            form {
                background: #fff;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
                width: 500px;
            }
            label {
                display: block;
                margin-top: 10px;
                font-weight: bold;
            }
            input[type="text"], input[type="number"] {
                width: 100%;
                padding: 8px;
                margin-top: 5px;
                border: 1px solid #ccc;
                border-radius: 5px;
            }
            input[type="submit"] {
                margin-top: 20px;
                padding: 10px 20px;
                background-color: #4CAF50;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }
            input[type="submit"]:hover {
                background-color: #45a049;
            }
        </style>
    </head>
    <body>
        <h2>Book Detail</h2>
        <%
            Book book = (Book) request.getAttribute("book");
            if (book != null) {
        %>
        <form action="MainController" method="post">
            <label>ID:</label>
            <input type="hidden" name="txtidbook" value="<%=book.getId()%>">
            <span><%=book.getId()%></span>

            <label>Title:</label>
            <input type="text" name="txttitlebook" value="<%=book.getTitle()%>">

            <label>Author:</label>
            <input type="text" name="txtauthorbook" value="<%=book.getAuthor()%>">

            <label>ISBN:</label>
            <input type="text" name="txtisbnbook" value="<%=book.getIsbn() != null ? book.getIsbn() : ""%>">

            <label>Category:</label>
            <input type="text" name="txtcategorybook" value="<%=book.getCategory() != null ? book.getCategory() : ""%>">

            <label>Published Year:</label>
            <input type="number" name="txtpublishedyearbook" value="<%=book.getPublished_year()%>">

            <label>Total Copies:</label>
            <input type="number" name="txttotalcopiesbook" value="<%=book.getTotal_copies()%>">

            <label>Available Copies:</label>
            <input type="number" name="txtavailablecopiesbook" value="<%=book.getAvailable_copies()%>">

            <label>Status:</label>
            <input type="text" name="txtstatusbook" value="<%=book.getStatus() != null ? book.getStatus() : ""%>">

            <label>Picture URL:</label>
            <input type="text" name="txturlbook" value="<%=book.getUrl()%>">

            <input type="submit" name="action" value="save change this book">
        </form>
        <% } else { %>
            <p>Book not found or invalid book ID.</p>
        <% } %>
    </body>
</html>
