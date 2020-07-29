<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">

</head>
<body>

<c:if test="${not empty reservationResult}">
    <p class="success">${reservationResult}</p>
</c:if>

<div class="create-box">
 <form action="${pageContext.request.contextPath}/addReservation" method="post" >
     <label>
         Start date: <br>
         <input type="datetime-local" name="startDate">

         End date: <br>
         <input type="datetime-local" name="endDate">

         Select room: <br>
         <select name="roomId">
             <c:forEach var="room" items="${requestScope.rooms}">
                 <option value="${room.id}">${room.roomNumber} ${room.type}</option>
             </c:forEach>
         </select>


         Select user: <br>
         <div>
             <select name="userId">
                 <c:forEach var="user" items="${requestScope.users}">
                     <option value="${user.id}">${user.firstName} ${user.secondName}</option>
                 </c:forEach>
             </select>
         </div>


         Manipulation name: <br>
         <input type="text" name="manipulationName">
         <br>

         Manipulation description:<br>
         <textarea placeholder="Type manipulation description" name="manipulationDescription"></textarea>
         <br>

         <input type="submit" value="Submit" class="btn">
     </label>
 </form>
</div>
 <c:if test="${not empty error}">
     <p class="success">${error}</p>
 </c:if>
</body>
</html>
