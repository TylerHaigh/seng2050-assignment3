<%-- 
    Document   : EditProfile
    Created on : 05/10/2014, 4:10:48 PM
    Author     : Tyler 2
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${empty userSession}">
	<c:redirect url="Login.jsp" />
</c:if>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
