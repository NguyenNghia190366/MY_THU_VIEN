<%-- 
    Document   : index
    Created on : Jun 3, 2025, 9:00:47 PM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/guestHomePage.css">
    </head>
    <body> 
        <section id="header">
            <div class="logo">
                <a href="MainController?action=home"><img src="#" alt="mini-logo"></a>
            </div>

            <div class="navbar">
                <nav>
                    <ul>
                        <li><a href="MainController?action=home">Home</a></li>
                        <li><a href="#">Library Info</a></li>
                        <li><a href="#">Contact Us</a></li>
                        <li><a href="viewcart.jsp">view cart</a></li>
                    </ul>

                    <a href="SignUp.html"> <button type="button">Sign up</button></a>
                    <a href="Login.jsp"> <button type="button">Login</button></a>

                </nav>

            </div>
        </section>

        <section id="sec1">
            <div class="sec1-left">
                <h1>Welcome to Hman Library — Heaven of Books</h1>
                <p>
                    Hman Library is a simple online platform that supports the management 
                    and borrowing of physical books in a small community library. 
                    The website does not provide eBooks — all loans are for physical copies, 
                    picked up in person.
                </p>

                <form class="search-form" action="MainController" method="post">
                    <input type="hidden" name="action" value="search" />
                    <select name="searchby">
                        <option value="">--Search all--</option>
                        <option value="Title" ${sessionScope.SEARCH_BY eq 'Title' ? 'selected' : ''}>Title</option>
                        <option value="Author" ${sessionScope.SEARCH_BY eq 'Author' ? 'selected' : ''}>Author</option>
                        <option value="Category" ${sessionScope.SEARCH_BY eq 'Category' ? 'selected' : ''}>Category</option>
                    </select>

                    <input type="text" name="txtsearch" placeholder="Enter title, author or title....."value="${sessionScope.SEARCH_KEYWORD}" />

                    <input type="submit" value="Search" />
                </form>


            </div>
            <div class="sec1-right pic" ></div>
        </section>

        <section id="sec2">
            <div class="sec2-container">
                <h3>Explore Our Latest and Most Popular Books</h3>
                <p>
                    Stay updated with the newest additions to our library.
                    Discover the books that our community loves the most.
                </p>

                <div class="grid-container">
                    <c:forEach var="book" items="${LIST_NEW}">
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
                                    <form action='BookController' method='get'>
                                        <input type='hidden' name='action' value='borrow' />
                                        <input type='hidden' name='txtid' value='${book.id}' />
                                        <input type='hidden' name='source' value='guestHome' />
                                        <input type='hidden' name='txtsearch' value='${sessionScope.SEARCH_KEYWORD}' />
                                        <input type="submit" value="Request Borrow" />
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <b>Don't have any new book</b><br/>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:forEach>
                </div>

                <form class="loadMore" action="BookController">
                    <input type="hidden" name="action" value="loadMoreNew" />
                    <input type="hidden" name="page" value="${CURRENT_PAGE + 1}" />
                    <button type="submit">More</button>
                </form>

            </div>

        </section>

        <section id="sec3">
            <div class="sec3-container">
                <h3>Your Community Library</h1>
                    <p>Easily borrow and return physical books today!</p>

                    <div class="sec3-content">
                        <div class="sec3-content-left">
                            <img src="#" alt="Support">
                            <h4>User Support and Assistance</h4>
                            <p>We're here to help you navigate our library.</p>
                            <a href="#">Learn more ></a>
                        </div>

                        <div class="sec3-content-mid">
                            <img src="#" alt="Support">
                            <h4>Explore Our Collection</h4>
                            <p>Browse popular books available at your nearest location</p>
                            <a href="#">View Câtlog ></a>
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

        <section id="sec4">
            <div class="sec4-container">
                <div class="sec4-left">
                    <h1>Join Our Library Today!</h1>
                    <p>Sign up or log in to borrow your favorite books.</p>
                </div>

                <div class="sec4-right">
                    <a href="SignUp.html"> <button type="button">Sign up</button></a>
                    <a href="Login.jsp"> <button type="button">Login</button></a>            
                </div>
            </div>
        </section>

        <%@ include file="footer.jsp" %>



        

    </body>
</html>

