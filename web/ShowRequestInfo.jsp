<%-- 
    Document   : ShowRequestInfo
    Created on : Jun 18, 2025, 12:42:01 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dto.Book"%>
<%@page import="dto.BookRequest"%>
<%@page import="dao.BookRequestDAO"%>
<%@page import="dto.User"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            form{
                width: 100px;
                height: 25px;
            }
            button{
                width: 100%;
                height: 100%;
            }
        </style>
    </head>
    <body>
        <%
        BookRequest re = null;
        BookRequestDAO d = new BookRequestDAO();
        int check=0;
        ArrayList<BookRequest> list = d.showRequest();
        String a = null;
        int id = 0;
        for(int i = 1;i<=list.size();i++){
        a="showrequest"+i;
        String idstring = (String)request.getParameter(a);
        if(idstring!=null){
                id = Integer.parseInt(idstring);
                check++;
                break;
        }
        
        }
        if(check==0){a="No data for this book";}
        re = d.findRequestByID(id);
        
            
        %>
        <h2>User Info</h2>
        <div>User ID:<%=re.getUser().getId()%></div>
        <div>User Name:<%=re.getUser().getName()%></div>
        <div>Email:<%=re.getUser().getEmail()%></div>
        <h2>Book Info</h2>
        <div>Book ID: <%=re.getBook().getId()%></div>
        <div>Title: <%=re.getBook().getTitle()%></div>
        <div>Author: <%=re.getBook().getAuthor()%></div>
        <div>Isbn: <%=re.getBook().getIsbn()%></div>
        <div>Book's Category: <%=re.getBook().getCategory()%></div>
        <div>Published year: <%=re.getBook().getPublished_year()%></div>
        <div>Total Copies: <%=re.getBook().getTotal_copies()%></div>
        <div>Available Copies: <%=re.getBook().getAvailable_copies()%></div>
        <div>image url: <%=re.getBook().getUrl()%></div>
        <%
        if(re.getStatus().equals("pending")){
        %>
        <form action="MainController" method="post">
            <input type="hidden" name="requestId" value="<%=re.getBook().getId()%>">
            <button class='approve' name="action" value="approve" type="submit">approve</button>
        </form>
        <form action="MainController" method="post">
            <input type="hidden" name="requestId" value="<%=re.getBook().getId()%>">
            <button class="reject" name="action" value="reject" type="submit">reject</button>
        </form>
        <%  } else if(re.getStatus().equals("approved")) {  %>    
        <form action="MainController" method="post">
            <input type="hidden" name="requestId" value="<%=re.getBook().getId()%>">
            <button class="borrowed" name="action" value="borrowed" type="submit">borrowed</button>
        </form>
        <%
            }
        %>
    </body>
</html>
