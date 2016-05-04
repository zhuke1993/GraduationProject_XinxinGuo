<%--
  Created by IntelliJ IDEA.
  User: ZHUKE
  Date: 2016/3/29
  Time: 15:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>new agreement</title>
</head>
<body>
<form method="post" action="/transaction/agreement">

    借款原因：<input name="loanFor" type="text"><br>
    借款标题：<input name="loanTitle" type="text"><br>
    金额：<input name="amount" type="text"><br>
    月利率：<input name="rateMonthly" type="text"><br>
    借款描述：<input name="description" type="text"><br>
    <input type="submit" value="登陆"/>
</form>
</body>
</html>
