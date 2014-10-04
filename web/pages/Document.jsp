<%-- 
    Document   : Document
    Created on : 29/09/2014, 7:39:35 PM
    Author     : Tyler 2
--%>

<%@page import="rgms.infrastructure.Session"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Session userSession = (Session)session.getAttribute("session");
    if (userSession == null) response.sendRedirect("Login.jsp");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>
