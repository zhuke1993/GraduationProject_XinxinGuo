<%--
  Created by IntelliJ IDEA.
  User: GXX
  Date: 2016/3/28
  Time: 1:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>register</title>
</head>
<body>
${msg}
<form method="post" action="/security/register">
    email: <input name="email" type="text"/><br>
    code:<input name="code" type="text"/><br>
    password: <input name="password" type="text"/><br>
    phoneNo: <input name="phoneNo" type="text"/><br>
    <input type="submit" value="submit"/>
    <input type="reset" value="reset"/>
</form>
</body>
</html>
