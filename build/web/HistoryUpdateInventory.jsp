<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Inventory History</title>
    </head>
    <body>
        <a href="MainController?action=home">Back to Home</a>
        <h2>Inventory History</h2>

        <c:if test="${not empty MSG}">
            <p>${MSG}</p>
        </c:if>

        <c:if test="${not empty HISTORY_LIST}">
            <table>
                <tr>
                    <th>ID</th>
                    <th>Admin Name</th>
                    <th>Book ID</th>
                    <th>Title</th>
                    <th>Added Quantity</th>
                    <th>Created Date</th>
                    <th>Expected Add Date</th>
                    <th>Actual Add Date</th>
                    <th>Status</th>
                    <th>Note</th>
                    <th>Action</th>
                </tr>
                <c:forEach var="o" items="${HISTORY_LIST}">
                    <tr>
                    <form action="InventoryController" method="post">
                        <input type="hidden" name="action" value="updateStatus"/>
                        <input type="hidden" name="inventory_id" value="${o.inventory_id}"/>

                        <td>${o.inventory_id}</td>
                        <td>${o.admin_name}</td>
                        <td>${o.book_id}</td>
                        <td>${o.book_title}</td>
                        <td>${o.quantity}</td>
                        <td>${o.created_date}</td>
                        <td>${o.expected_add_date}</td>
                        <td>
                            <input 
                                type="date" 
                                name="actual_add_date" 
                                value="${o.actual_add_date}" 
                                ${o.status != 'pending' ? 'disabled' : ''} 
                                />
                        </td>



                        <td>
                            <select name="status" ${o.status != 'pending' ? 'disabled' : ''}>
                                <option value="pending" ${o.status == 'pending' ? 'selected' : ''}>Pending</option>
                                <option value="completed" ${o.status == 'completed' ? 'selected' : ''}>Completed</option>
                                <option value="cancelled" ${o.status == 'cancelled' ? 'selected' : ''}>Cancelled</option>
                            </select>
                        </td>
                        <td>
                            <input type="text" name="note" value="${o.note}" ${o.status != 'pending' ? 'readonly' : ''}/>
                        </td>
                        <td>
                            <c:if test="${o.status == 'pending'}">
                                <button type="submit">Update</button>
                            </c:if>
                        </td>

                    </form>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 40px;
            background: #f9f9f9;
        }
        h2 {
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background: #fff;
            box-shadow: 0 0 10px rgba(0,0,0,0.05);
        }
        th, td {
            border: 1px solid #ddd;
            padding: 12px 15px;
            text-align: left;
        }
        th {
            background: #007BFF;
            color: #fff;
        }
        tr:nth-child(even) {
            background: #f2f2f2;
        }
        tr:hover {
            background: #e9f5ff;
        }
        p {
            color: #d9534f;
        }
        select, input[type="text"] {
            padding: 5px;
        }
        button {
            background: #28a745;
            color: #fff;
            border: none;
            padding: 6px 12px;
            cursor: pointer;
        }
        button:hover {
            background: #218838;
        }
    </style>
</body>
</html>
