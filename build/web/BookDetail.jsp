<%-- 
    Document   : BookDetail
    Created on : Jun 14, 2025, 11:45:46 PM
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
    </head>
    <body>
        <%
        Book book = null;
        BookDAO d = new BookDAO();
        int check=0;
        ArrayList<Book> list = d.viewBookList();
        String a = null;
        int id = 0;
        for(int i = 1;i<=list.size();i++){
        a="book"+i;
        String idstring = request.getParameter(a);
        if(idstring!=null){
                id = Integer.parseInt(idstring);
                check++;
                break;
        }
        
        }
        if(check==0){a="No data for this book";}
        else{
            book = d.findBookById(id);
            }
            
        if(book!=null){
        %>
        <form action="MainController" method="post">
        ID: <%=id%><input type="hidden" name="txtidbook" value="<%=id%>">
        Title: <input type="text" name="txttitlebook" value="<%=book.getTitle()%>">
        Author: <input type="text" name="txtauthorbook" value="<%=book.getAuthor()%>">
        Isbn: <input type="text" name="txtisbnbook" value="<%=book.getIsbn()%>">
        Category: <input type="text" name="txtcategorybook" value="<%=book.getCategory()%>">
        Published year: <input type="number" name="txtpublishedyearbook" value="<%=book.getPublished_year()%>">
        Total copies: <input type="number" name="txttotalcopiesbook" value="<%=book.getTotal_copies()%>">
        Available copies: <input type="number" name="txtavailablecopiesbook" value="<%=book.getAvailable_copies()%>">
        Status: <input type="text" name="txtstatusbook" value="<%=book.getStatus()%>">
        Picture URL: <input type="text" name="txturlbook" value="<%=book.getUrl()%>">
        
        <input type="submit" name="action" value="save change this book">
        
        </form>
        <%
            }
        %>      
    </body>
</html>
