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
            .booklist{
                display: flex;
                justify-content: center;
            }
            .book{
                margin: 10px 10px;
                width: 18%;
            }
            .book form{
                width: 100%;
                height: 100%;
            }
            button{
                
                width: 100%;
                height: 100%;
            }
        </style>
    </head>
    <body>
        <div class = "booklist">
            
            
        <%
            BookDAO d = new BookDAO();
            ArrayList<Book> list = d.viewBookList();
            for(int i = 0;i<list.size();i++){       
        %>
        <div class = "book">
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
