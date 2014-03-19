<%-- 
    Document   : newArticle
    Created on : 19 mars 2014, 22:05:33
    Author     : alainlesage
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Article</title>
    </head>
    <body>
        <h1>New Article</h1>
        <form action="#" method="POST">
            <input type="text" required="true" name="title" placeholder="Title"/><br/>
            <textarea rows="3" name="content" placeholder="Content"/></textarea><br/>
            <input type="submit" value="Save"/>
        </form>
    </body>
</html>
