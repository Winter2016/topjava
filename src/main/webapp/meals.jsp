<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="ru.javawebinar.topjava.model.MealWithExceed"%>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<c:forEach items=${requestScope["meals"] var="item">
    Name:<%item.getDescription(); %>
    <br>
</c:forEach>
</body>
</html>