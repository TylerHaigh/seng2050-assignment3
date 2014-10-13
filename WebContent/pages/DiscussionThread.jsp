<%-- 
    Document   : DiscussionThread
    Created on : 29/09/2014, 7:40:10 PM
    Author     : Tyler 2
--%>

<%@page import="rgms.infrastructure.Session"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${empty userSession}">
	<c:redirect url="Login.jsp" />
</c:if>

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
