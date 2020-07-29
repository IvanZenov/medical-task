
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new medical room</title>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body>

    <div class="create-box">
        <h1>Add new:</h1>
        <form action="${pageContext.request.contextPath}/addNewRoom" method="post">
            <label>
                <br>
                <input type="number" min="0" name="roomNumber" placeholder="Room number"><br>
                <input type="text" name="type" placeholder="Room type"><br>

                <input type="submit" value="Submit">
            </label>
        </form>
    </div>
</body>
</html>
