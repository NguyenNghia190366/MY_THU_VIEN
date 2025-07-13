<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Book Detail</title>
        <link rel="stylesheet" href="css/bookDetailUser.css">
    </head>
    <body>
        <section id="header">
            <div class="logo">
                <a href="MainController?action=home"><img src="#" alt="mini-logo"></a>
            </div>

            <div class="navbar">
                <nav>
                    <ul>
                        <li><a href="MainController?action=home">Back to Home</a></li>
                        <li><a href="viewcart.jsp">View Cart</a></li>

                        <c:set var="lastCategory" value="" />
                        <c:forEach var="cookie" items="${pageContext.request.cookies}">
                            <c:if test="${cookie.name eq 'lastCategory'}">
                                <c:set var="lastCategory" value="${cookie.value}" />
                            </c:if>
                        </c:forEach>

                        <li>
                            <a href="BookController?action=search&txtsearch=${lastCategory}">Continue Shopping</a>
                        </li>
                    </ul>


                    <a href="ViewProfile.jsp"> <button type="button">Profile</button></a>
                    <a href="LogoutController"> <button type="button">Logout</button></a>

                </nav>

            </div>
        </section>

        <c:if test="${not empty BOOK_DETAIL}">
            <div>
                <h2> ${BOOK_DETAIL.title} </h2>
                <img src="${BOOK_DETAIL.url}" alt="Book Cover" style="max-width:200px;"/><br/>
                <p><strong>Author:</strong> ${BOOK_DETAIL.author}</p>
                <p><strong>ISBN:</strong> ${BOOK_DETAIL.isbn}</p>
                <p><strong>Category:</strong> ${BOOK_DETAIL.category}</p>
                <p><strong>Published Year:</strong> ${BOOK_DETAIL.published_year}</p>
                <p><strong>Total Copies:</strong> ${BOOK_DETAIL.total_copies}</p>
                <p><strong>Available:</strong> ${BOOK_DETAIL.available_copies}</p>
                <p><strong>Status:</strong> ${BOOK_DETAIL.status}</p>

                <c:if test="${BOOK_DETAIL.available_copies > 0}">
                    <c:set var="found" value="false"/>
                    <c:if test="${not empty sessionScope.CART}">
                        <c:forEach var="b" items="${sessionScope.CART}">
                            <c:if test="${b.id == BOOK_DETAIL.id}">
                                <c:set var="found" value="true"/>
                            </c:if>
                        </c:forEach>
                    </c:if>

                    <c:choose>
                        <c:when test="${found}">
                            <button disabled>Already in Cart</button>
                        </c:when>
                        <c:otherwise>
                            <form action="BookController" method="get">
                                <input type="hidden" name="action" value="borrow" />
                                <input type="hidden" name="txtid" value="${BOOK_DETAIL.id}" />
                                <input type="hidden" name="returnUrl" value="MainController?action=bookDetail&bookID=${BOOK_DETAIL.id}" />
                                <input type="submit" value="Request Borrow" />
                            </form>
                        </c:otherwise>
                    </c:choose>

                </c:if>
                <c:if test="${BOOK_DETAIL.available_copies == 0}">
                    <p><em>Currently unavailable.</em></p>
                </c:if>
            </div>
        </c:if>

        <c:if test="${empty BOOK_DETAIL}">
            <p>Book not found.</p>
        </c:if>

        <%@ include file="footer.jsp" %>
    </body>
</html>
