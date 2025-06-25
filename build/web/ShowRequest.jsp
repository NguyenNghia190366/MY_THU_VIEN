<%-- 
    Document   : ShowRequest
    Created on : Jun 17, 2025, 9:41:34 PM
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
            .request{
                margin: 10px 20px;
                display: flex;
                justify-content: space-between;
            }
            .header{
                margin: 10px 20px;
                display: flex;
                justify-content: space-between;
            }
            .titleheader{
                font-size: 20px
            }
        </style>
    </head>
    <body>
        <%
        BookRequestDAO d = new BookRequestDAO();
        ArrayList<BookRequest> list = d.showRequest();
        %>
        <h1>ALL BOOK REQUEST: </h1>
        
        <table>
  <tr>
    <th>ID</th>
    <th>User Name</th>
    <th>Email</th>
    <th>Book</th>
    <th>Request Date</th>
    <th></th>
  </tr>

        <%
        for(int i = 0;i<list.size();i++){
        %>
        <tr>
    <td><%=list.get(i).getId()%></td>
    <td><%=list.get(i).getUser().getName()%></td>
    <td><%=list.get(i).getUser().getEmail()%></td>
    <td><%=list.get(i).getBook().getTitle()%></td>
    <td><%=list.get(i).getRequest_date()%></td>
    <td><form action="ShowRequestInfo.jsp" method = "post"><button name="showrequest<%=list.get(i).getId()%>" value="<%=list.get(i).getId()%>" type="submit">show info</button></form></td> 
    
        </tr>
        <%
           }  
        %>
    </table>
        </body>
</html>
