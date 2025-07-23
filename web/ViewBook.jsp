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


        <!-- Hiện thông báo nếu có -->
        <c:if test="${not empty sessionScope.ERROR_MESSAGE}">
            <p style="color:red;">${sessionScope.ERROR_MESSAGE}</p>
            <c:remove var="ERROR_MESSAGE" scope="session"/>
        </c:if>

        <div class="nav-bar">
            <a href="MainController?action=home">Home</a>

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
            <span style="color: red">${MSG}</span>
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
                                            <button onclick="borrowBook(this, ${book.id})">Request Borrow</button>
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
            // Biến này lấy từ session, giá trị set trong JSP:
            window.isLoggedIn = ${sessionScope.USER != null ? 'true' : 'false'};

            function borrowBook(btn, bookId) {
                // Lấy URL hiện tại để return về đúng ViewBook.jsp + query
                const returnUrl = window.location.pathname + window.location.search;

                if (!window.isLoggedIn) {
                    // Chưa login: build REDIRECT_BACK_TO = link borrow
                    const borrowUrl = "BookController?action=borrow"
                            + "&txtid=" + bookId
                            + "&returnUrl=" + encodeURIComponent(returnUrl);

                    window.location.href = "Login.jsp?REDIRECT_BACK_TO=" + encodeURIComponent(borrowUrl);
                    return;
                }

                // Đã login: gửi POST borrow luôn
                const params = new URLSearchParams();
                params.append("action", "borrow");
                params.append("txtid", bookId);
                params.append("returnUrl", returnUrl);

                fetch("BookController", {
                    method: "POST",
                    body: params
                })
                        .then(res => {
                            if (res.ok) {
                                btn.disabled = true;
                                btn.innerText = "Already in Cart";
                                // Reload để giữ state search mới an toàn
                                window.location.href = returnUrl;
                            } else {
                                alert("Borrow failed!");
                            }
                        })
                        .catch(err => {
                            console.error(err);
                            alert("Error borrowing book!");
                        });
            }
        </script>

    </body>

</html>
