<%-- 
    Document   : AdminDashboard.jsp
    Created on : Jun 13, 2025, 11:04:16 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            body{
                display: flex;
                margin: 0px;
            }
            .menu{
                padding-top: 20px;
                display: grid;
                width: 17%;
                background-color: #006666;
                padding-left: 20px;
            }
            .mainContent{
                
                padding-left: 20px;
                padding-top: 20px;
                width: 83%;
                background-color: #9cdbc4;
            }
            .mainContent a{
                color: white;
            }
            .menu a{
                color: white;
            }
            
        </style>
    </head>
    <body>
        <div class = "menu">
            <a href="SystemConfig.jsp">System config</a>
            <a href="ViewBook.jsp">View Books</a>
            <a href="ShowRequest.jsp">View Requests</a>
            <a href="ShowBorrowRecord.jsp">View Borrow Records</a>
        </div>
        <div class = "mainContent">bbbbbbbbbb</div>
    </body>
</html>
