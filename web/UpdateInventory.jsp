<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Update Inventory</title>
    </head>
    <body>
        
        <a href="AdminDashboard.jsp">Back to Home</a>
        <h3>New Inventory Order</h3>
        <form action="InventoryController" method="post">
            <input type="hidden" name="action" value="createInventory"/>
            <input type="hidden" name="bookID" value="${param.bookID}"/>

            <p>Book ID: ${param.bookID}</p>

            <label>Added Quantity:</label>
            <input type="number" name="quantity" required/><br/>

            <label>Expected Add Date:</label>
            <input type="date" name="expected_date" required/><br/>

            <label>Note:</label>
            <textarea name="note"></textarea><br/>

            <button type="submit">Create Inventory Order</button>
        </form>

        <style>
            body {
                font-family: Arial, sans-serif;
                padding: 40px;
                background: #f9f9f9;
            }

            h3 {
                color: #333;
            }

            form {
                background: #fff;
                padding: 20px 30px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
                max-width: 500px;
            }

            form p {
                margin-bottom: 10px;
                font-weight: bold;
            }

            label {
                display: block;
                margin: 12px 0 4px;
                color: #555;
            }

            input[type="number"],
            input[type="date"],
            textarea {
                width: 100%;
                padding: 8px 12px;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
            }

            textarea {
                height: 80px;
            }

            button {
                margin-top: 15px;
                padding: 10px 20px;
                background: #007bff;
                color: #fff;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }

            button:hover {
                background: #0056b3;
            }
        </style>


    </body>
</html>
