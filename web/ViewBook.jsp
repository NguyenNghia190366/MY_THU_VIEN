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

        <p>ROLE = ${sessionScope.ROLE}</p>

        <div class="nav-bar">
            <c:choose>
                <c:when test="${sessionScope.ROLE eq 'user'}">
                    <a href='UserDashboard.jsp'>Home</a>
                </c:when>
                <c:otherwise>
                    <a href='MainController?action=home'>Home</a>
                </c:otherwise>
            </c:choose>

            <a href="viewcart.jsp">View Cart</a>
        </div>

        <h1>Search Results</h1>

        <form class="search-form" action="BookController" method="get">
            <select name="searchby">
                <option value="">--Search all--</option>
                <option value="Title" ${sessionScope.SEARCH_BY eq 'Title' ? 'selected' : ''}>Title</option>
                <option value="Author" ${sessionScope.SEARCH_BY eq 'Author' ? 'selected' : ''}>Author</option>
                <option value="Category" ${sessionScope.SEARCH_BY eq 'Category' ? 'selected' : ''}>Category</option>
            </select>

            <input type="hidden" name="action" value="search" />
            <input type="text" name="txtsearch" placeholder="Enter title, author or category..." value="${sessionScope.SEARCH_KEYWORD}" />

            <input type="submit" value="Search" />
        </form>

        <c:choose>
            <c:when test="${not empty BOOK_RESULT}">
                <div class="book-container">
                    <c:forEach var="book" items="${BOOK_RESULT}">
                        <div class="book-card">
                            <a href="MainController?action=bookDetail&bookID=${book.id}">
                                <img src="${book.url}" alt="Book Cover" style="cursor:pointer;" />
                            </a>

                            <p><strong>Title:</strong>
                                <a href="MainController?action=bookDetail&bookID=${book.id}">
                                    ${book.title}
                                </a>
                            </p>

                            <p><strong>Author:</strong>
                                <a href="BookController?action=search&txtsearch=${book.author}">
                                    ${book.author}
                                </a>
                            </p>

                            <p><strong>Category:</strong>
                                <a href="BookController?action=search&txtsearch=${book.category}">
                                    ${book.category}
                                </a>
                            </p>

                            <p><strong>Available Copies:</strong> ${book.available_copies}</p>

                            <c:choose>
                                <c:when test="${book.available_copies > 0}">
                                    <c:set var="found" value="false"/>
                                    <c:if test="${not empty sessionScope.CART}">
                                        <c:forEach var="b" items="${sessionScope.CART}">
                                            <c:if test="${b.id == book.id}">
                                                <c:set var="found" value="true"/>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>

                                    <c:choose>
                                        <c:when test="${found}">
                                            <button disabled>Already in Cart</button>
                                        </c:when>
                                        <c:otherwise>
                                            <button onclick="borrowBook(this, ${book.id}); return false;">Request Borrow</button>
                                        </c:otherwise>
                                    </c:choose>

                                </c:when>
                                <c:otherwise>
                                    <span class="out-of-stock">Out of stock</span>
                                </c:otherwise>
                            </c:choose>

                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <p class="no-result">No books found.</p>
            </c:otherwise>
        </c:choose>

        <script>
            const isLoggedIn = ${sessionScope.user != null ? 'true' : 'false'};

            function borrowBook(btn, bookId) {
                const currentUrl = "BookController?action=search&txtsearch=${sessionScope.SEARCH_KEYWORD}";

                if (!isLoggedIn) {
                    // Nếu chưa login → đi Login.jsp + mang URL quay về
                    window.location.href = "Login.jsp?redirectBackTo=" + encodeURIComponent(currentUrl);
                    return;
                }

                const params = new URLSearchParams();
                params.append('action', 'borrow');
                params.append('txtid', bookId);
                params.append('returnUrl', currentUrl);

                fetch('BookController?' + params.toString())
                    .then(res => {
                        if (res.ok) {
                            btn.disabled = true;
                            btn.innerText = 'Already in Cart';
                        } else {
                            alert("Failed to borrow book!");
                        }
                    })
                    .catch(err => {
                        console.error(err);
                        alert("Error!");
                    });
            }
        </script>

    </body>
</html>
