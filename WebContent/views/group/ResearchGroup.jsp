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
		
    </c:otherwise>
</c:choose>
	<h1>${groupName }</h1>
	<!-- LIST MEMBERS -->
	<h2> Members</h2>
	<ul>
	<c:forEach var="member" items="${ groupMembers }" >
   	   	<li>
   	   		<!-- Link to users Profile -->
   	   		<a href="${pageContext.request.contextPath}/account/profile?userName=${member}" class="list-group-item">
    			<c:out value="${ member }" />
    		</a>
   	   	</li>   		
   	</c:forEach>
   	</ul>
	
	<!-- Link to group discussion board -->
	

