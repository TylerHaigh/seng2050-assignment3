<%-- 
    Document   : Navigation
    Created on : 04/10/2014, 4:57:15 PM
    Author     : Tyler 2
--%>

<%@page import="rgms.infrastructure.Session"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head></head>
    <body>
        
        <ul id="target" class="nav nav-pills">
            <li><a href="Dashboard.jsp">Home</a></li>
            
            <li class="dropdown">
    			<a href="#" class="dropdown-toggle" data-toggle="dropdown" >Research Groups <span class="caret"></span>
    			</a>
    			<ul class="dropdown-menu">
                    <li><a href="#" data-toggle="dropdown">Group 1</a></li>
                    <li><a href="#">Group 2</a></li>
                </ul>
            </li>

            <li><a href="#">Discussion Board</a></li>
            
            <li class="dropdown">
            	<a href="#" class="dropdown-toggle" data-toggle="dropdown" >
	                <c:out value="${userSession.user.fullName}" />
	                <span class="caret"></span>
                </a>
                <ul class="dropdown-menu" >
                    <li><a href="Profile.jsp?userId=${userSession.user.studentID}">View Profile</a></li>
                    <li><a href="Redirector.jsp?logout=true">Log Out</a></li>
                </ul>
            </li>
        </ul>
        
    </body>
</html>
