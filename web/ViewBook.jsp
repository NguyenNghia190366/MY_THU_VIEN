<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Result</title>
        <link rel="stylesheet" href="css/style.css" />
    </head>
    <body>
        <div class="nav-bar">
            <a href="GuestHomePage.jsp">Home</a> | <a href="viewcart.jsp">View Cart</a>
        </div>

        <h1>Search Results</h1>

        <form class="search-form" action="BookController" method="get">
            <input type="hidden" name="action" value="search" />
            <input type="text" name="txtsearch" value="${sessionScope.SEARCH_KEYWORD}" />
            <input type="submit" value="Search" />
        </form>

        <c:choose>
            <c:when test="${not empty BOOK_RESULT}">
                <div class="book-container" style="">
                    <c:forEach var="book" items="${BOOK_RESULT}">
                        <div class="book-card">
                            <form action="BookController" method="get">
                                <input type="hidden" name="action" value="borrow" />
                                <input type="hidden" name="txtid" value="${book.id}" />
                                <input type="hidden" name="txtsearch" value="${SEARCH_KEYWORD}" />
                                <img src="${book.url}" alt="Book Image" class="book-image"/>
                                <p><strong>ID:</strong> ${book.id}</p>
                                <p><strong>Title:</strong> ${book.title}</p>
                                <p><strong>Category:</strong> ${book.category}</p>
                                <p><strong>Available Copies:</strong> ${book.available_copies}</p>
                                <c:choose>
                                    <c:when test="${book.available_copies > 0}">
                                        <input type="submit" value="Request Borrow" />
                                    </c:when>
                                    <c:otherwise>
                                        <span class="out-of-stock">Out of stock</span>
                                    </c:otherwise>
                                </c:choose>
                            </form>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <p class="no-result">No books found.</p>
            </c:otherwise>
        </c:choose>
    </body>
</html>
