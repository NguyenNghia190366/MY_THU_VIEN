<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>User Dashboard</title>
        <link rel="stylesheet" href="css/guestHomePage.css">
        <script src="js/guestHomePage.js"></script>
    </head>
    <body>

        <% if (request.getAttribute("LIST_AVAILABLE") == null) {
            response.sendRedirect("MainController?action=home");
        } %>


        <!-- Hiện thông báo nếu có -->
        <c:if test="${not empty sessionScope.msg}">
            <p style="color:red;">${sessionScope.msg}</p>
            <c:remove var="msg" scope="session"/>
        </c:if>


        <section id="header">
            <div class="logo">
                <a href="MainController?action=home"><img src="#" alt="mini-logo"></a>
            </div>

            <div class="navbar">
                <nav>
                    <ul>
                        <li><a href="MainController?action=home">Home</a></li>
                        <li><a href="#">Library Info</a></li>
                        <li><a href="#">View History</a></li>
                        <li><a href="viewcart.jsp">view cart</a></li>
                    </ul>

                    <a href="ViewProfile.jsp"> <button type="button">Profile</button></a>
                    <a href="LogoutController"> <button type="button">Logout</button></a>

                </nav>

            </div>
        </section>

        <section id="sec1">
            <div class="sec1-left">
                <h1>Welcome back, ${sessionScope.user.name}</h1>
                <p>We're glad to see you again! Explore our library and find your next great read.</p>

                <form class="search-form" action="BookController" method="get">
                    <select name="searchby">
                        <option value="">--Search all--</option>
                        <option value="Title" ${sessionScope.SEARCH_BY eq 'Title' ? 'selected' : ''}>Title</option>
                        <option value="Author" ${sessionScope.SEARCH_BY eq 'Author' ? 'selected' : ''}>Author</option>
                        <option value="Category" ${sessionScope.SEARCH_BY eq 'Category' ? 'selected' : ''}>Category</option>
                    </select>

                    <input type="hidden" name="action" value="search" />
                    <input type="text" name="txtsearch" placeholder="Enter title, author or title....."value="${sessionScope.SEARCH_KEYWORD}" />

                    <input type="submit" value="Search" />
                </form>
            </div>
        </section>

        <section id="sec2">
            <div class="sec2-container">
                <h3>Find Somthing New to Read From Our In-stock Collection.</h3>
                <p>
                    Stay updated with the newest additions to our library.
                    Discover the books that our community loves the most.
                </p>

                <div class="carousel-container"> 
                    <button class="prev">&#10094;</button>

                    <div class="carousel-container"> 

                        <div class="carousel-track">
                            <c:choose>
                                <c:when test="${not empty LIST_AVAILABLE}">
                                    <c:forEach var="book" items="${LIST_AVAILABLE}">
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

                                            <p><strong>Available:</strong> ${book.available_copies}</p>

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
                                </c:when>
                                <c:otherwise>
                                    <p>No books available.</p>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <button class="next">&#10095;</button>
                        <div class="dots">
                            <span class="dot active"></span>
                            <span class="dot"></span>
                            <span class="dot"></span>
                        </div>
                    </div>

                </div>

        </section>

        <section id="sec3">
            <div class="sec3-container">
                <h3>Explore Our Extensive Collection of Books and Their Availability.</h1>


                    <div class="sec3-content">
                        <div class="sec3-content-left">
                            <img src="#" alt="Support">
                            <h4>User Support and Assistance</h4>
                            <p>We're here to help you navigate our library.</p>
                            <a href="#">Learn more ></a>
                        </div>

                        <div class="sec3-content-right">
                            <img src="#" alt="Support">
                            <h4>Sign Up for a Library Card</h4>
                            <p>Get your free digital library card to borrow books and access services.</p>
                            <a href="#">Register now ></a>
                        </div>

                    </div>

            </div>
        </section>

        <%@ include file="footer.jsp" %>

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
