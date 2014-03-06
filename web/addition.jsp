<%-- 
    Document   : newjsp
    Created on : 27 févr. 2014, 11:03:33
    Author     : alainlesage
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Résultat</title>
    </head>
    <body>
        <h1>Votre SUPERBE résultat : </h1>
        <% 
            String resultat ="null";
            try 
            {
                float nombre1 = Float.parseFloat(request.getParameter("nombre1").toString());                
                float nombre2 = Float.parseFloat(request.getParameter("nombre2").toString());
                Float nombreResultat = nombre1 + nombre2;
                resultat = nombreResultat.toString();
            }
            catch (Exception e)
            {
                resultat = e.getMessage();
            }
        %>
        <p> SALUT <%= resultat%></p>
    </body>
</html>
