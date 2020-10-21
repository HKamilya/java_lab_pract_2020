<%--
  Created by IntelliJ IDEA.
  User: gipot
  Date: 21.10.2020
  Time: 19:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
    <title>Login Success Page</title>
</head>
<body>
<%
    String userName = null;
    String sessionID = null;
    Cookie[] cookies = request.getCookies();
    if(cookies !=null){
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("user")) userName = cookie.getValue();
        }
    }
%>
<h3>Hi <%=userName %>, do the checkout.</h3>
<br>
<form action="Logout" method="post">
    <input type="submit" value="Logout" >
</form>
</body>
</html>
