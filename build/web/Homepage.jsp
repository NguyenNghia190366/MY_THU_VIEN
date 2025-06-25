<%-- 
    Document   : Homepage
    Created on : Jun 11, 2025, 4:48:17 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "java.util.ArrayList"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/GuestHomePage.css">
        <style></style>
    </head>
    <body>
        <section id="header">
            <div class="logo">
                <a href="UserDashboard.jsp"><img src="#" alt="mini-logo"></a>
            </div>

            <div class="navbar">
                <nav>
                    <ul>
                        <li><a href="UserDashboard.jsp">Home</a></li>
                        <li><a href="#">Book Brows</a></li>
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

                <form action="SearchBookController">
                    <input type="text" name="txtsearch" value="<%= (request.getParameter("txtsearch")!=null)?request.getParameter("txtsearch"):"" %>"/>
                    <input type="submit" value="find"/>
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

                <div class="carousel-container"> <!-- Đổi tên class cho đúng -->
                    <button class="prev">&#10094;</button>

                    <div class="carousel-track">
                        <%
                       // ArrayList<Book> list = null;//(ArrayList<Book>) request.getAttribute("LIST_NEW");
                       // if (list != null && !list.isEmpty()) {
                       //     for (Book book : list) {
                        %>
                        
                        <%
                        //        }
                         //   } else {
                        %>
                        <p>No new books available.</p>
                        <%
                       //     }
                        %>

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

        <footer id="footer">
            <div class="ft-up">
                <img src="#" alt="logomini">

                <ul class="ft-up-list">
                    <li><a href="#" alt="">About Us</a></li>
                    <li><a href="#" alt="">Contact Us</a></li>
                    <li><a href="#" alt="">Help Center</a></li>
                    <li><a href="#" alt="">Blog Post</a></li>
                </ul>

                <div class="ft-up-icon">
                    <a href="#" alt=""><i class="fa-solid fa-user"></i></a>
                    <a href="#" alt=""><i class="fa-regular fa-user"></i></a>
                    <a href="#" alt=""><i class="fa-light fa-user"></i></a>
                    <a href="#" alt=""><i class="fa-thin fa-user"></i></a>

                </div>
            </div>

            <div>
                <a href="#" alt="">© 2025 Hman. All rights reserved.</a>
                <a href="#" alt="">Privacy Policy</a>
                <a href="#" alt="">Terms of Use</a>
                <a href="#" alt="">Cookie Policy</a>
            </div>
        </footer>



    </body>
</html>
