<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
    <title>Reservations</title>
</head>
<body>

<table style="border: 4px double black; border-collapse: collapse;">
    <thead>
    <tr>
        <th>Id</th>
        <th>Start date</th>
        <th>End date</th>
        <th>Active</th>
        <th>Room number</th>
        <th>Room type</th>
        <th>Username</th>
        <th>User id</th>
        <th>Manipulation name</th>
        <th>Change active:</th>
    </tr>
    </thead>
    <c:forEach items="${requestScope.reservations}" var="reservations">
        <tbody>
        <tr>
            <td>${reservations.id}</td>
            <td>${reservations.startDate}</td>
            <td>${reservations.endDate}</td>
            <td>${reservations.active}</td>
            <td>${reservations.roomNumber}</td>
            <td>${reservations.roomType}</td>
            <td>${reservations.username}</td>
            <td>${reservations.userId}</td>
            <td>${reservations.manipulationName}</td>

            <td>
                <c:choose>
                    <c:when test="${reservations.active eq true}">
                        <a href="all-reservations?id=${reservations.id}">Cancel reservation</a>
                    </c:when>
                </c:choose>
            </td>
        </tr>
        </tbody>
    </c:forEach>
</table>


</body>
</html>
