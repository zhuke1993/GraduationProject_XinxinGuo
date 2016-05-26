<%--
  Created by IntelliJ IDEA.
  User: GXX
  Date: 2016/3/28
  Time: 0:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
</head>
<body>
${msg}<br>
<form method="post" action="/security/login">
    UserName: <input name="username" type="text"/><br>
    Password: <input name="password" type="password"/><br>
    <input type="submit" value="submit"/>
    <input type="reset" value="reset"/>
</form>
</body>
</html>
