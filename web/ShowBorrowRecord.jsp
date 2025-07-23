<%-- 
    Document   : ShowBorrowRecord
    Created on : Jun 21, 2025, 9:02:40 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dao.BorrowRecordDAO"%>
<%@page import="dao.FineDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dto.BorrowRecord"%>
<%@page import="dto.Fine"%>
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
            
        BorrowRecordDAO d = new BorrowRecordDAO();
        FineDAO fd = new FineDAO();          
        
        ArrayList<BorrowRecord> list = d.showBorrowRecord();
        
        %>
        <h1>ALL BORROW RECORDS: </h1>
        
        <table>
  <tr>
    <th>ID</th>
    <th>User Name</th>
    <th>Email</th>
    <th>Book</th>
    <th>Borrow Date</th>
    <th>Due Date</th>
    <th>Return Date</th>
    <th>Status</th>
    <th>Fine</th>
    <th></th>
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
    <td><%=list.get(i).getBorrow_date()%></td>
    <td><%=list.get(i).getDue_date()%></td>
    <td><%=list.get(i).getReturn_date()%></td>
    <td><%=list.get(i).getStatus()%></td>
    <%      
        if(fd.showAFineByID(list.get(i).getId())!=null){
        int re = fd.updateFine(list.get(i).getId());
    %>
    <td><%=fd.showAFineByID(list.get(i).getId()).getFind_amount()%></td>
    <% 
    } else {
    int re = fd.insertFine(list.get(i).getId());
    
    %>
    <td><%= fd.showAFineByID(list.get(i).getId()).getFind_amount() %></td>
    <% 
    }  %>
    
    <td><form action="ShowBorrowRecordInfo.jsp" method = "post"><button name="showrequest<%=list.get(i).getId()%>" value="<%=list.get(i).getId()%>" type="submit">show info</button></form></td> 
    
        </tr>     
        <%
           }  
        %>
    </table>
    </body>
</html>
