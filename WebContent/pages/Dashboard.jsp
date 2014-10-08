<%-- 
    Document   : Dashboard
    Created on : 29/09/2014, 7:36:49 PM
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
        <title>Dashboard</title>
        <link rel="stylesheet" type="text/css" href="../References/Styles.css" />
        <link rel="stylesheet" type="text/css" href="../References/bootstrap/css/bootstrap.css" />
        <link rel="stylesheet" type="text/css" href="../References/bootstrap/css/bootstrap-theme.css" />
        <script src="../References/jquery.js"></script> 
        <script src="../References/Scripts.js"></script> 
    </head>
    <body>
        <div class="wrapper">
            <div class="header">
            	<div class="headerNav"></div>
            </div>
            
            <div class="main">
                <h1>Dashboard</h1>
            </div>
                
            <div class="footer">
                 <p>Copyright The Aggregates 3.0 (2014)</p>
            </div>
            
        </div>
    </body>
</html>
