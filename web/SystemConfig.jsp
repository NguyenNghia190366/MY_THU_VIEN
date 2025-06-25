<%-- 
    Document   : SystemConfig
    Created on : Jun 14, 2025, 12:05:58 AM
    Author     : Admin        <h1>Hello World!</h1>

--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dto.SystemConfig"%>
<%@page import="dao.SystemConfigDAO"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
        SystemConfigDAO d = new SystemConfigDAO();
        ArrayList<SystemConfig> list = d.getConfigList();
        if(list!=null && !list.isEmpty()){
/*        
 */
        %>
        <form  action="MainController" method = "post">

            overdue_fine_per_day: <input type="text" name="txtoverduefineperday" value="<%=list.get(0).getConfig_value()%>">
            default_borrow_duration_days: <input type="text" name="txtborrowdurationdayd" value="<%=list.get(1).getConfig_value()%>">
            unit_price_per_book: <input type="text" name="txtunitpriceperbook" value="<%=list.get(2).getConfig_value()%>">
            <input class="submit" type="submit" name="action" value="Change">

        </form>
        <%}
            if(session.getAttribute("ANOUNNCED") != null){
            out.print(session.getAttribute("ANOUNNCED"));}
        %>
    </body>
</html>
