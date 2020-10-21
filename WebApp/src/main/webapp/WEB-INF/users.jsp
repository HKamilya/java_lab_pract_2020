<%@ page import="ru.itis.javalab.models.User" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: gipot
  Date: 20.10.2020
  Time: 21:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<table>
    <tr>
        <th>First Name</th>
        <th>Last Name</th>
    </tr>
    <% List<User> users = (List<User>) request.getAttribute("usersForJsp");
        for (User user : users) { %>
    <tr>
        <td><%=user.getFirstName()%>
        </td>
        <td><%=user.getLastName()%>
        </td>
    </tr>
    <% } %>
</table>
</body>
</html>
