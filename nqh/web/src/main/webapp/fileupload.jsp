<%--
  Created by IntelliJ IDEA.
  User: GXX
  Date: 2016/4/4
  Time: 19:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传</title>
</head>
<body>
<form method="post" action="/upload/file" enctype="multipart/form-data">
    <input type="file" name="file">
    <input type="submit">
</form>
</body>
</html>
