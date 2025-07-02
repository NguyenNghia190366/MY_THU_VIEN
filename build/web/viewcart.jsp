<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart</title>
    </head>
    <body>
        <h1>Your Cart</h1>
        <div>
            <a href="BookController?action=search&txtsearch=${sessionScope.SEARCH_KEYWORD}">Continue Shopping</a>
        </div>

        <c:choose>
            <c:when test = "${not empty sessionScope.CART}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Title</th>
                            <th>Author</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="book" items="${sessionScope.CART}">
                            <tr>
                                <td>${book.id}</td>
                                <td>${book.title}</td>
                                <td>${book.author}</td>
                                <td>
                                    <a href="BookController?action=remove&id=${book.id}" >Remove</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <<form action="SendBorrowRequestController">
                    <input type="submit" value="Submit borrow request"/>
                </form>
            </c:when>
            <c:otherwise>
                <p>Your cart is empty.</p>
            </c:otherwise>
        </c:choose>
    </body>
</html>