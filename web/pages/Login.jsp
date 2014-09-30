<%-- 
    Document   : Login
    Created on : 30/09/2014, 7:23:59 PM
    Author     : Tyler 2
--%>

<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:useBean id="database" class="rgms.model.DatabaseBean" />
<jsp:useBean id="user" class="rgms.model.User" scope="session" />

<%
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    
    //if (user != null) response.sendRedirect("Dashboard.jsp");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>Logging you in...</h1>
        <%
            String query = "SELECT * FROM Users WHERE Username='" + username +
                "' AND Password='" + password + "'";
            
            database.setQuery(query);
            
            ResultSet rs = database.getData();
            
            if (rs != null) {
                while (rs.next()) {
                    String name = rs.getString("Username");
                    String pass = rs.getString("Password");
                    
                    if (name.equals(username) && pass.equals(password)) {
                        response.sendRedirect("Dashbard.jsp");
                    } else {
                        response.sendRedirect("../index.html");
                    }
                }
            }
        %>
        
    </body>
</html>
