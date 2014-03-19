<%-- 
    Document   : login.jsp
    Created on : 19 mars 2014, 16:51:19
    Author     : alainlesage
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Log in</title>
    </head>
    <body>
        <h1>Log in</h1>
        <form action="Login" method="POST">
            <input type="text" required="true" name="pseudo" placeholder="Username"/>
            <input type="password" required="true" name="password" placeholder="Password"/>
            <input type="submit" value="OK"/>
        </form>
    </body>
</html>
