<%-- 
    Document   : ResearchGroup
    Created on : 29/09/2014, 7:38:07 PM
    Author     : Tyler 2
--%>

<%@page import="rgms.infrastructure.Session"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
	<c:when test="${empty profileUser}">
		<!-- FILL THIS IN WITH GROUP NAME -->
	</c:when>
	<c:otherwise>
		<h1>
    	</h1>
    </c:otherwise>
</c:choose>
	<div>
		${groupName }
	</div>

