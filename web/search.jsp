<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.Book" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Search Books</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                padding: 30px;
            }
            table {
                border-collapse: collapse;
                width: 80%;
            }
            th, td {
                border: 1px solid #999;
                padding: 8px 12px;
            }
            th {
                background-color: #f2f2f2;
            }
            .search-form {
                margin-bottom: 20px;
            }
            .book-url img {
                width: 80px;
                height: auto;
            }
        </style>
    </head>
    <body>
        <h2>Search for Books</h2>

        <!-- Search form -->
        <form action="SearchBookController" method="get" class="search-form">
            <input type="text" name="txtsearch" placeholder="Enter title, author or category..." 
                   value="<%= (request.getParameter("txtsearch") != null) ? request.getParameter("txtsearch") : "" %>" />
            <input type="submit" value="Search" />
        </form>

        <!-- Display result -->
        <%
            List<Book> list = (List<Book>) request.getAttribute("list");
            if (list != null) {
                if (list.isEmpty()) {
        %>
        <p>No books found.</p>
        <%
                } else {
        %>
        <table>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Author</th>
                <th>Available Copies</th>
                <th>Image</th>
                <th>Actions</th>
            </tr>
            <%
                for (Book b : list) {
            %>
            <tr>
                <td><%= b.getId() %></td>
                <td><%= b.getTitle() %></td>
                <td><%= b.getAuthor() %></td>
                <td><%= b.getAvailable_copies() %></td>
                <td class="book-url">
                    <img src="<%= b.getUrl() %>" alt="Book cover">
                </td>
                <td>
                    <a href="ViewBookDetailController?id=<%= b.getId() %>">View Detail</a>
                </td>
            </tr>
            <%
                }
            %>
        </table>
        <%
                }
            }
        %>

    </body>
</html>
