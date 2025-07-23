<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.BorrowRecord" %>
<%@ page import="dto.Book" %>
<%@ page import="java.text.SimpleDateFormat" %>

<html>
<head>
    <title>Borrow History</title>
</head>
<body>
    <a href="MainController?action=home">Back to Home</a>
    <h2>Borrow History</h2>

    <%
        List<BorrowRecord> borrowHistory = (List<BorrowRecord>) request.getAttribute("borrowHistory");

        if (borrowHistory == null || borrowHistory.isEmpty()) {
    %>
        <p>No borrow records found.</p>
    <%
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    %>
        <table border="1">
            <tr>
                <th>Book Title</th>
                <th>Borrow Date</th>
                <th>Due Date</th>
                <th>Return Date</th>
                <th>Status</th>
            </tr>
    <%
            for (BorrowRecord record : borrowHistory) {
                Book book = record.getBook();
    %>
            <tr>
                <td><%= book != null ? book.getTitle() : "Unknown" %></td>
                <td><%= sdf.format(record.getBorrow_date()) %></td>
                <td><%= sdf.format(record.getDue_date()) %></td>
                <td><%= record.getReturn_date() == null ? "Not returned" : sdf.format(record.getReturn_date()) %></td>
                <td><%= record.getStatus() %></td>
            </tr>
    <%
            }
    %>
        </table>
    <%
        }
    %>

</body>
</html>
