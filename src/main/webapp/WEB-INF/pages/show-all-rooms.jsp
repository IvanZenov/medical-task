<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Rooms</title>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">

</head>
<body>
<h2>All Rooms</h2>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Description</th>

    </tr>
    </thead>
    <c:forEach items="${requestScope.rooms}" var="room">
        <tbody>
            <tr>
                <td>${room.id}</td>
                <td>${room.roomNumber}</td>
                <td>${room.type}</td>
            </tr>
        </tbody>
    </c:forEach>
</table>


</body>
</html>
