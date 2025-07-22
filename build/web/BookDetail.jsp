<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dto.Book"%>
<%@page import="dto.User"%>
<%
    Book book = (Book) request.getAttribute("book");
    if (book == null) {
        out.println("<p>Book not found or invalid ID.</p>");
        return;
    }

    String role = (String) session.getAttribute("role");
    User user = (User) session.getAttribute("USER");
%>

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
            form {
                width: 500px;
                background: #fff;
                padding: 20px;
                border-radius: 12px;
                box-shadow: 0 0 10px rgba(0,0,0,0.15);
            }
            label {
                margin-top: 12px;
                font-weight: bold;
                display: block;
            }
            input[type="text"], input[type="number"] {
                width: 100%;
                padding: 8px;
                margin-top: 5px;
                border: 1px solid #ccc;
                border-radius: 6px;
            }
            .action-buttons {
                margin-top: 20px;
                display: flex;
                gap: 10px;
                flex-wrap: wrap;
            }
            input[type="submit"] {
                padding: 10px 20px;
                background-color: #007BFF;
                color: white;
                border: none;
                border-radius: 6px;
                cursor: pointer;
            }
            input[type="submit"]:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>

        <% if (book != null) { %>
        <h2>Book Detail</h2>
        <form>
            <label>ID:</label>
            <span><%= book.getId() %></span>

            <label>Title:</label>
            <span><%= book.getTitle() %></span>

            <label>Author:</label>
            <span><%= book.getAuthor() %></span>

            <label>ISBN:</label>
            <span><%= book.getIsbn() != null ? book.getIsbn() : "" %></span>

            <label>Category:</label>
            <span><%= book.getCategory() != null ? book.getCategory() : "" %></span>

            <label>Published Year:</label>
            <span><%= book.getPublished_year() %></span>

            <label>Total Copies:</label>
            <span><%= book.getTotal_copies() %></span>

            <label>Available Copies:</label>
            <span><%= book.getAvailable_copies() %></span>

            <label>Status:</label>
            <span><%= book.getStatus() != null ? book.getStatus() : "" %></span>

            <label>Picture URL:</label>
            <span><%= book.getUrl() %></span>
        </form>

        <div class="action-buttons">
            <% if ("Admin".equalsIgnoreCase(role)) { %>
            <form action="MainController" method="post">
                <input type="hidden" name="txtidbook" value="<%= book.getId() %>">
                <input type="submit" name="action" value="save change this book">
            </form>
            <% } %>

            <% if ("User".equalsIgnoreCase(role) && user != null) { %>
            <!-- Form cho Borrow -->
            <form action="BookRequestController" method="post">
                <input type="hidden" name="id" value="<%= book.getId() %>">
                <input type="hidden" name="action" value="borrow">
                <input type="submit" value="Borrow Book">
            </form>

            <!-- Form cho Return -->
            <form action="BookRequestController" method="post">
                <input type="hidden" name="id" value="<%= book.getId() %>">
                <input type="hidden" name="action" value="return">
                <input type="submit" value="Return Book">
            </form>
            <% } %>
        </div>

        <% if (request.getAttribute("message") != null) { %>
        <p style="color: green;"><%= request.getAttribute("message") %></p>
        <% } %>
        <% if (request.getAttribute("error") != null) { %>
        <p style="color: red;"><%= request.getAttribute("error") %></p>
        <% } %>

        <% } else { %>
        <h3>Book not found or invalid ID.</h3>
        <% } %>

    </body>
</html>
