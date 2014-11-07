<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
<h1>${ groupName }</h1>

<c:if test="${ joinSuccess }">
	Join was successful
</c:if>

<c:choose>
	<c:when test="${ notMember }">
		<a href="${ pageContext.request.contextPath }/group/invite?groupId=${ groupId }">
			Join this Group
		</a>
	</c:when>
	
	<c:otherwise>
		<a href="${ pageContext.request.contextPath }/group/leave?groupId=${ groupId }">Leave Group</a>
	</c:otherwise>
</c:choose>

<h2>Members</h2>

<div class="list-group">
   	<c:forEach var="member" items="${ groupMembers }" >
   		<a href="${pageContext.request.contextPath}/account/profile?userName=${member}" class="list-group-item">
   			<c:out value="${ member }" />
   		</a>
   	</c:forEach>
</div>

<hr />

<c:if test="${ notMember eq false }">
	<h2>Meetings</h2>
	<div class="list-group">
	    <c:if test="${ empty groupMeetings }">
	        <p>No Meetings have been created</p>
	    </c:if>
	    <c:forEach var="meeting" items="${ groupMeetings }" >
	        <a href="${pageContext.request.contextPath}/group/meeting?meetingId=${meeting.id}" class="list-group-item">
	            <c:out value="When: ${ meeting.dateDue }" /><br>
	            <c:out value="Description: ${ meeting.description }" />
	        </a>
	    </c:forEach>
	</div>
	
	<hr />
	
	<h2>Discussions </h2>
	
	<div class="list-group">
	    <c:if test="${ empty groupDiscussions }">
	        <p>No Discussions have been created</p>
	    </c:if>
	    <c:forEach var="thread" items="${ groupDiscussions }" >
	        <a href="${pageContext.request.contextPath}/group/discussion?threadId=${thread.id}" class="list-group-item">
	            <c:out value="${ thread.threadName }" />
	        </a>
	    </c:forEach>
	</div>
	
	<a href="${pageContext.request.contextPath}/group/creatediscussion/?groupId=${ groupId }" class="btn btn-primary">Create Discussion</a>
	
	<hr />
	
	<h2>Documents</h2>
	
	<div class="list-group">
	    <c:if test="${ empty groupDocuments }">
	        <p>No Documents have been uploaded</p>
	    </c:if>
	    
	    <c:forEach var="document" items="${ groupDocuments }" >
	        <a href="${pageContext.request.contextPath}/document/document?threadId=${document.threadId}" class="list-group-item">
	            <c:out value="File Name: ${ document.documentName }" />
	        </a>
	    </c:forEach>
	</div>	
	
	<a href="${pageContext.request.contextPath}/group/creatediscussion/?groupId=${ groupId }" class="btn btn-primary">Upload New Document</a>
</c:if>

