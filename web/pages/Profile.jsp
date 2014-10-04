<%-- 
    Document   : Profile
    Created on : 29/09/2014, 7:37:31 PM
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
        <title>My Profile</title>
        <link rel="stylesheet" type="text/css" href="../References/Styles.css" />
        <script src="../References/jquery.js"></script> 
        <script src="../References/Scripts.js"></script> 
    </head>
    <body>
        <div class="wrapper">
            <div class="header">
                <div class="nav"></div>
            </div>
            
            <div class="details">
                    
            </div>
            
            <div class="researchGroups">
                
            </div>
            
            <div class="footer">
                
            </div>
            
        </div>
    </body>
</html>
