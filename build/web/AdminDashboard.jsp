<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Admin Dashboard</title>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    </head>
    <body>


        <!-- Check login -->
        <c:if test="${empty sessionScope.USER}">
            <c:redirect url="Login.jsp"/>
        </c:if>


        <!-- Show error message if any -->
        <c:if test="${not empty sessionScope.ERROR_MESSAGE}">
            <p style="color:red;">${sessionScope.ERROR_MESSAGE}</p>
            <c:remove var="ERROR_MESSAGE" scope="session"/>
        </c:if>

        <h4>Welcome ${sessionScope.USER.name} comeback</h4>
        <p><a href="LogoutController">Logout</a></p>
        <p><a href="ViewProfile.jsp">Change Profile</a></p>

        <!-- 📊 Statistics -->
        <section class="statistics">
            <h2>Library Statistics</h2>

            <table border="1">
                <tr>
                    <th>Total Books</th>
                    <th>Total Users</th>
                    <th>Currently Borrowed Books</th>
                    <th>Most Borrowed Books</th>
                    <th>Average Borrow Duration</th>
                </tr>
                <tr>
                    <td>${TOTAL_BOOKS}</td>
                    <td>${TOTAL_USERS}</td>
                    <td>${CURRENTLY_BORROWED}</td>
                    <td>
                        <ul>
                            <c:forEach var="book" items="${TOP_BORROWED_BOOKS}">
                                <li>${book.title} (${book.borrowCount} times)</li>
                                </c:forEach>
                        </ul>
                    </td>
                    <td>${AVG_BORROW_DURATION} days</td>
                </tr>
            </table>

            <!-- Chart outside table -->
            <div class="chart-container">
                <canvas id="monthlyChart"></canvas>
            </div>

            <script>
                const ctx = document.getElementById('monthlyChart').getContext('2d');
                const monthlyData = {
                    labels: [
                <c:forEach var="m" items="${MONTHLY_STATS}">
                        '${m.month}',
                </c:forEach>
                    ],
                    datasets: [{
                            label: 'Borrow count',
                            data: [
                <c:forEach var="m" items="${MONTHLY_STATS}">
                    ${m.borrowCount},
                </c:forEach>
                            ],
                            backgroundColor: '#007BFF'
                        }]
                };
                new Chart(ctx, {
                    type: 'bar',
                    data: monthlyData
                });
            </script>
        </section>

        <!-- 🛠️ Admin Actions -->
        <section class="actions">
            <h2>Admin Actions</h2>
            <ul>
                <li><a href="SystemConfig.jsp">Setup System Config</a></li>
                <li><a href="ViewBookAdmin.jsp">Add/Edit/Remove Books</a></li>
                <li><a href="ShowBorrowRecord.jsp">View Borrow / Return</a></li>
                <li><a href="ShowRequest.jsp">View Requests</a></li>
                <li><a href="ManagerUser.jsp">Search & Manage Accounts</a></li>
                <li><a href="InventoryController?action=viewHistory">View History Update</a></li>
            </ul>
        </section>

        <!-- 🔎 Search Bar -->
        <form action="BookController" method="get">
            <input type="hidden" name="action" value="showAll"/>
            <input type="text" name="keyword" placeholder="Search by Title" value="${param.keyword}"/>
            <button type="submit">Search</button>
        </form>

        <!-- 📚 Book Table -->
        <c:if test="${not empty LIST_BOOKS}">
            <div class="book-table">
                <table>
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>ISBN</th>
                        <th>Category</th>
                        <th>Total</th>
                        <th>Available</th>
                        <th>Status</th>
                        <th>Update</th>
                    </tr>
                    <c:forEach var="b" items="${LIST_BOOKS}">
                        <tr>
                            <td>${b.id}</td>
                            <td>
                                <img src="${b.url}" alt="${b.title}" width="40" height="40"/>
                                ${b.title}
                            </td>
                            <td>${b.isbn}</td>
                            <td>${b.category}</td>
                            <td>${b.total_copies}</td>
                            <td>${b.available_copies}</td>
                            <td>${b.status}</td>
                            <td><a href="UpdateInventory.jsp?bookID=${b.id}">Update</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </c:if>

        <!-- MSG nếu có -->
        <c:if test="${not empty MSG}">
            <p style="color:red;">${MSG}</p>
        </c:if>

        <style>
            table {
                border-collapse: collapse;
                width: 100%;
            }
            th, td {
                padding: 8px 12px;
                border-bottom: 1px solid #ddd;
                text-align: left;
            }
            th {
                background-color: #f5f5f5;
            }
            tr:hover {
                background-color: #f1f1f1;
            }
            img {
                border-radius: 4px;
            }
            .statistics {
                margin-bottom: 40px;
            }
            .chart-container {
                width: 100%;
                max-width: 800px;
                margin: 20px auto;
            }
        </style>

    </body>
</html>
