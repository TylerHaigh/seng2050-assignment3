<%-- 
    Document   : Login
    Created on : 30/09/2014, 7:23:59 PM
    Author     : Tyler 2
--%>

<%@page import="java.util.Enumeration"%>
<%@page import="rgms.controller.LoginController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
    LoginController lc = new LoginController(request.getSession());
    String autoLogin = lc.autoLogin();
    if (autoLogin == null) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean error = Boolean.parseBoolean(request.getParameter("error"));
        pageContext.setAttribute("error", error);

        if (username != null && password != null)
            response.sendRedirect(lc.login(username, password));
    } else response.sendRedirect(autoLogin);
    
%>

<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Login | Research Group Management System</title>
        <link rel="stylesheet" type="text/css" href="../References/Styles.css" />
    </head>
    <body>
        <div class="wrapper">
            
            <div class="header">
                
            </div>
            
            <div class="main">
                
                <c:if test="${error}">
                    <p>Error: Invalid username or password</p>
                </c:if>
                
                
                <form method="get" action="Login.jsp">
                    <label for="username">Username: </label>
                    <input type="text" name="username" id="username" /> <br />
                    
                    <label for="password">Password: </label>
                    <input type="password" name="password" id="password" /> <br />
                    
                    <input type="submit" value="Login"  />
                </form>
            </div>
            
            <div class="footer">
                <p>Copyright The Aggregates 3.0 (2014)</p>
            </div>
            
        </div>
    </body>
</html>
