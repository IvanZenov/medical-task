
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new medical room</title>
</head>
<body>
<h1>Add new user:</h1>
<div>
    <form action="${pageContext.request.contextPath}/addNewUser" method="post" class="create-box">
        <label>
            First name: <br>
            <input type="text" name="firstName" placeholder="Write your first name"><br>

            Second name: <br>
            <input type="text" name="secondName" placeholder="Write your second name"><br>

            Role: <br>
            <input type="text" name="role" placeholder="Input role"><br>

            Gender: <br>
            <input type="radio" name="gender" value="Male" checked="checked" > Male</input>
            <input type="radio" name="gender" value="Female"> Female</input>
            <br>

            Phone number: <br>
            <input type="tel" name="phoneNumber">
            <br>
            <input type="submit" value="Submit">
        </label>
    </form>
</div>
</body>
</html>
