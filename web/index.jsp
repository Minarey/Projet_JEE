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
        <%
            /* Chargement du driver JDBC pour MySQL */
            try {
                Class.forName( "com.mysql.jdbc.Driver" );
            } catch ( ClassNotFoundException e ) {
                /* Gérer les éventuelles erreurs ici. */
            }
            
            /* Connexion à la base de données */
            String url = "jdbc:mysql://localhost:3306/projet_web_jee";
            String utilisateur = "admin";
            String motDePasse = "lenfant";
            Connection connexion = null;
            try {
                connexion = DriverManager.getConnection( url, utilisateur, motDePasse );

                out.println("connexion OK pour l'utilisateur " + utilisateur);

            } catch ( SQLException e ) {
                out.println("connexion KO" + e.getMessage() + e.getSQLState());
            } finally {
                if ( connexion != null )
                    try {
                        /* Fermeture de la connexion */
                        connexion.close();
                    } catch ( SQLException ignore ) {
                        /* Si une erreur survient lors de la fermeture, il suffit de l'ignorer. */
                    }
            }
        %>
        <%@ include file="header.jsp" %>
        <div>
            <%@ include file="article.jsp" %>
        </div>
        <%@ include file="footer.jsp" %>
    </body>
</html>
