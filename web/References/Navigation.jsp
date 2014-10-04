<%-- 
    Document   : Navigation
    Created on : 04/10/2014, 4:57:15 PM
    Author     : Tyler 2
--%>

<!-- http://line25.com/tutorials/how-to-create-a-pure-css-dropdown-menu -->

<%@page import="rgms.infrastructure.Session"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head></head>
    <body>
        <ul>
            <li><a href="Dashboard.jsp">Home</a></li>
            
            <li>
                My Research Groups
                <ul>
                    <li>Group 1</li>
                    <li>Group 2</li>
                </ul>
            </li>

            <li>Discussion Board</li>
            <li><a href="Profile.jsp">
                <%
                    Session s = (Session)request.getSession().getAttribute("session");
                    out.print(s.getUser().getFullName());
                %>
                </a>
                <ul>
                    <li><a href="Profile.jsp">View Profile</a></li>
                    <li><a href="Redirector.jsp?logout=true">Log Out</a></li>
                </ul>
            </li>
        </ul>
    </body>
</html>
