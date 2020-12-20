
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
    <title>Login Success Page</title>
</head>
<body>
Hello user!
<a href="/Logout">logout</a>
<h3>${_csrf_token}</h3>
<form action="/Profile?action=delete&user_id=${user_id}" method="post">
    <input type="submit" value="delete">
</form>
</body>
</html>