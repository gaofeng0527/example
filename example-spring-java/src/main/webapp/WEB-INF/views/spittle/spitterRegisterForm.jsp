<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019-01-24
  Time: 10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>spitterRegisterForm</title>
</head>
<body>
<h1>Register</h1>
<form action="/spitter/register" method="post">
    First Name: <input type="text" name="firstName"/><br>
    Last Name: <input type="text" name="lastName"/><br>
    User Name: <input type="text" name="userName"/><br>
    Pass Word: <input type="password" name="password"/><br>
    <input type="submit" value="Register">
</form>
</body>
</html>
