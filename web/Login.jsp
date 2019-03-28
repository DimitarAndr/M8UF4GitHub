<%--
  Created by IntelliJ IDEA.
  User: dimitar
  Date: 22/03/19
  Time: 15:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
    <form name="loginForm" method="post" action="shopServlet">
    <div id="nameClient">
        <input type="text" name="nameClient" placeholder="Name"  maxlength="10">
        <input type="password" name="password" placeholder="Password">
        <input class="button" type="submit" id="send" value="send">
    </div>
    </form>
</head>
<body>

</body>
</html>
