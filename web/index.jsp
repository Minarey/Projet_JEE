<%-- 
    Document   : index
    Created on : 10 mars 2014, 14:32:23
    Author     : alainlesage
--%>

<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Blog</title>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <div>

            ${affichage}

            <%@ include file="article.jsp" %>
        </div>
        <%@ include file="footer.jsp" %>
    </body>
</html>
