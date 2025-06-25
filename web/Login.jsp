<%-- 
    Document   : Login
    Created on : Jun 12, 2025, 12:52:37 PM
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
                justify-content: center;
                background-color: black;
            }
            .form{
                padding: 20px;
                position: absolute;
                top:30px;
                text-align: center;
                font-size: 18px;
                width: 40%;
                background-color: pink;
                border-radius: 10px;
            }
            .insideform{
               
                align-content: center;
                display: flex;
                font-size: 40px;
                margin:10px 0px;
            }
            .title{
                margin-right: 20px;
            }
            .input1{
                padding: 10px;
                font-size: 50%;
                width: 49.5%;
            }
            .input2{
                padding: 10px;
                font-size: 50%;
                width: 40%;
            }
            .submit{
                background-color: pink;
                font-size: 20px;
                width: 70%;
                height: 50px;
            }
        </style>
    </head>
    <body>

        <div class = "form" >
            <h1>Let sign in</h1>
            <form action="MainController" method = "post">
                <div class="insideform"><div class="title">Email: </div><input class="input1" type="email" name="txtemail" required=""></div>
                <div class="insideform"><div class="title">Password: </div><input class="input2" type="password" name="txtpassword" required=""></div>
                <input class="submit" type="submit" name="action" value='login'>
            </form>
        </div>
    </body>
</html>
