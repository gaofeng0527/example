<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019-01-23
  Time: 17:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Spittels</title>
</head>
<body>
<ul>
    <c:forEach items="${slist}" var="spittle">
        <li id="${spittle.id}">${spittle.message}&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate value="${spittle.time}" pattern="yyyy-MM-dd HH:mm:ss"/></li>
    </c:forEach>
</ul>
</body>
</html>
