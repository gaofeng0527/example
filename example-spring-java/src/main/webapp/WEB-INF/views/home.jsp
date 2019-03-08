<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019-01-23
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Home</title>
</head>
<body>

<h1>${uname} 你好，欢迎来到Spittr</h1>

<a href="<c:url value="/spittles"/>">spittles</a> | <a href="<c:url value="/spitter/register"/>">register</a>


</body>
</html>
