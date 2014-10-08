<%-- 
    Document   : Redirector
    Created on : 04/10/2014, 5:05:00 PM
    Author     : Tyler 2
--%>

<%
    boolean logout = Boolean.parseBoolean(request.getParameter("logout"));
    if (logout) {
        session.removeAttribute("session");
        response.sendRedirect("Login.jsp");
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Redirector</title>
    </head>
    <body>
        <h1>Your are being redirected. Please wait...</h1>
    </body>
</html>
