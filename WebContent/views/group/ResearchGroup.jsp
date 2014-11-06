<%-- 
    Document   : ResearchGroup
    Created on : 29/09/2014, 7:38:07 PM
    Author     : Tyler 2
--%>

<%@page import="rgms.infrastructure.Session"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
	<c:when test="${empty profileUser}">
		<!-- FILL THIS IN WITH GROUP NAME -->
	</c:when>
	<c:otherwise>
		
    </c:otherwise>
</c:choose>
	
<h1>${groupName }</h1>

<h2>Members</h2>
<div class="list-group">
   	<c:forEach var="member" items="${ groupMembers }" >
   		<a href="${pageContext.request.contextPath}/account/profile?userName=${member}" class="list-group-item">
   			<c:out value="${ member }" />
   		</a>
   	</c:forEach>
</div>

<hr />

<h2>Meetings</h2>
 
	<div class="list-group">
	   	<c:forEach var="meeting" items="${ groupMeetings }" >
	   		<a href="${pageContext.request.contextPath}/group/meeting?meetingId=${meeting.id}" class="list-group-item">
		   		<c:out value="When: ${ meeting.dateDue }" /><br>
		   		<c:out value="Description: ${ meeting.description }" />
	   		</a>
	   	</c:forEach>
	</div>
<hr />

<h2>Documents</h2>
<div class="list-group">
	   	<c:forEach var="document" items="${ groupDocuments }" >
	   		<a href="${pageContext.request.contextPath}/group/Document?documentId=${document.id}" class="list-group-item">
		   		<c:out value="Title: ${ document.documentName }" />
	   		</a>
	   	</c:forEach>
	</div>
	
	

