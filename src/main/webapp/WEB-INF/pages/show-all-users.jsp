<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All users</title>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">

</head>
<body>

<h2>All Users</h2>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>First name</th>
        <th>Second name</th>
        <th>Role</th>
        <th>Gender</th>
        <th>Phone number</th>
    </tr>
    </thead>
    <c:forEach items="${requestScope.users}" var="user">
        <tbody>
        <tr>
            <td>${user.id}</td>
            <td>${user.firstName}</td>
            <td>${user.secondName}</td>
            <td>${user.phoneNumber}</td>
            <td>${user.gender}</td>
            <td>${user.role}</td>
        </tr>
        </tbody>
    </c:forEach>
</table>

</body>
</html>
