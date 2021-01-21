<html lang="en">
<head>
    <title>Users</title>
</head>
<body>
<table>
    <tr>
        <th>First Name</th>
        <th>Last Name</th>
    </tr>

    <#list usersForJsp as user>
        <tr>
            <td>${user.firstName}
            </td>
            <td>${user.lastName}
            </td>
        </tr>
    </#list>
</table>
</body>
</html>
