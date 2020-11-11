<%--
  Created by IntelliJ IDEA.
  User: gipot
  Date: 21.10.2020
  Time: 19:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/Login" method="post">
    <br>
    <input type="text" name="username">
    <br>
    <input type="password" name="password">
    <br>

    <input type="submit">
</form>
</body>
</html>