<%-- 
    Document   : Document
    Created on : 29/09/2014, 7:39:35 PM
    Author     : Tyler 2
--%>

<%@page import="rgms.infrastructure.Session"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${empty userSession}">
	<c:redirect url="Login.jsp" />
</c:if>

    <body>
        <h1>Hello World!</h1>
    </body>
</html>
