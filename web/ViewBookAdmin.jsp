<%-- 
    Document   : ViewBook
    Created on : Jun 14, 2025, 10:47:47 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dto.Book"%>
<%@page import="dao.BookDAO"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            .book-container {
    display: grid;
    /* Chia thành 4 cột bằng nhau, mỗi cột có kích thước tối thiểu 200px nhưng không vượt quá 1fr (phần còn lại) */
    grid-template-columns: 25% 25% 25% 25%;
    gap: 20px; /* Khoảng cách giữa các item */
    padding: 20px; /* Khoảng cách lề xung quanh container */
    max-width: 1200px; /* Giới hạn chiều rộng tổng thể */
    margin: 0 auto; /* Căn giữa container */
}

.book-item {
    border: 1px solid #ddd;
    border-radius: 5px;
    padding: 15px;
    text-align: center;
    background-color: #fff;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    display: flex; /* Dùng flexbox bên trong item để căn chỉnh nội dung */
    flex-direction: column;
    justify-content: space-between; /* Đẩy nút xuống dưới cùng */
    height: 100%; /* Đảm bảo tất cả các item có cùng chiều cao */
    box-sizing: border-box; /* Bao gồm padding và border trong chiều rộng/cao */
}
.book-item button{
    background-color: papayawhip;
    width: 100%;
    height:100%;
}
.book-item img {
    max-width: 100%;
    height: auto;
    max-height: 180px; /* Giới hạn chiều cao của ảnh nếu cần */
    object-fit: contain; /* Đảm bảo ảnh không bị méo */
    margin-bottom: 10px;
}

.book-info h3 {
    margin: 10px 0 5px;
    font-size: 1.1em;
    color: #333;
}

.book-info .author {
    font-size: 0.9em;
    color: #666;
    margin-bottom: 5px;
}

.book-info .description {
    font-size: 0.85em;
    color: #777;
    margin-bottom: 10px;
    flex-grow: 1; /* Cho phép mô tả chiếm không gian còn lại */
}

.book-info .price {
    font-weight: bold;
    color: #e44d26; /* Màu cam hoặc màu nổi bật */
    margin-bottom: 15px;
    font-size: 1em;
}

.btn-details {
    display: inline-block;
    background-color: #007bff; /* Màu xanh dương */
    color: white;
    padding: 8px 15px;
    border-radius: 4px;
    text-decoration: none;
    font-size: 0.9em;
    transition: background-color 0.3s ease;
    margin-top: auto; /* Đẩy nút xuống cuối cùng của flex item */
}

.btn-details:hover {
    background-color: #0056b3;
}
        </style>
    </head>
    <body>
        <a href="MainController?action=home"> return to the home</a><br><br>
        <a href="AddBook.jsp">Add Book</a>
        <div class = "book-container">
            
            
        <%
            BookDAO d = new BookDAO();
            ArrayList<Book> list = d.viewBookList();
            for(int i = 0;i<list.size();i++){ 
        %>
        
        <div class = "book-item">
        <form action="BookDetail.jsp" method = "post">    
        <button name="book<%=list.get(i).getId()%>" value="<%=list.get(i).getId()%>" type="submit">
            <div>ID: <%=list.get(i).getId()%></div>
            <div><%=list.get(i).getTitle()%></div>
            <div><%=list.get(i).getAuthor()%></div>
            <div><%=list.get(i).getIsbn()%></div>
            <div><%=list.get(i).getCategory()%></div>
            <div><%=list.get(i).getPublished_year()%></div>
            <div>Total copies: <%=list.get(i).getTotal_copies()%></div>
            <div>Available copies: <%=list.get(i).getAvailable_copies()%></div>
            <%if(list.get(i).getStatus().equals("active")){%>
            <div style="color:greenyellow"><%=list.get(i).getStatus()%></div>
                     <%
            } else {
            %>
            <div style="color:red"><%=list.get(i).getStatus()%></div>
            </button>
            </form>
             
         <%
            }
            %>
            
       </div>
        
                    <%
            }
            %>
    </div>
        </body>
</html>
