<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h1>Dashboard</h1>

<!-- First row of Dashboard -->
<div class="row">
	<div class="col-md-6">
		<h2>Notifications</h2>
		
		<c:choose>
			<c:when test="${ fn:length(notifications) eq 0}">
				You have no notifications
			</c:when>
			
			<c:when test="${ fn:length(notifications) eq 1}">
				<a href="${ pageContext.request.contextPath }/home/notifications">
					You have 1 notification
				</a>
			</c:when>
			
			<c:otherwise>
				<a href="${ pageContext.request.contextPath }/home/notifications">
					You have <c:out value="${ fn:length(notifications) }" /> notifications
				</a>
			</c:otherwise>
			
		</c:choose>
	</div>
	
	<div class="col-md-6">
		<h2>Meetings</h2>
		
		<c:choose>
			<c:when test="${ fn:length(meetings) eq 0}">
				You have no meetings
			</c:when>
			
			<c:otherwise>
				<div class="list-group">
			    	<c:forEach var="meeting" items="${ meetings }" >
			    		<a href="${ pageContext.request.contextPath }/group/meeting?meetingId=${ meeting.id }"
			    		   class="list-group-item">
			    			<c:out value="${ meeting.description } : " />
			    			<c:out value="${ meeting.dateDue }" />
			    		</a>
			    	</c:forEach>
			    </div>
			</c:otherwise>
		</c:choose>
		
	</div>
</div>

<hr />

<!-- Second row of Dashboard -->
<div class="row">
	<div class="col-md-6">
		<h2>My Groups</h2>
		
		<c:choose>
			<c:when test="${ fn:length(groups) eq 0}">
				You do not belong to any research groups
			</c:when>
			
			<c:otherwise>
				<div class="list-group">
			    	<c:forEach var="group" items="${ groups }" >
			    		<a href="${pageContext.request.contextPath}/group/researchGroup?groupId=${group.id }" class="list-group-item">
			    			<c:out value="${ group.groupName }" />
			    		</a>
			    	</c:forEach>
			    </div>
			</c:otherwise>
		</c:choose>
		
	</div>
	
	<div class="col-md-6">
		<h2>My Documents</h2>
		
		<c:choose>
			<c:when test="${ fn:length(userDocuments) eq 0}">
				You have not uploaded any documents
			</c:when>
			
			<c:otherwise>
				<div class="list-group">
			    	<c:forEach var="document" items="${ userDocuments }" >
			    		<a href="${pageContext.request.contextPath}/document/document?documentId=${document.id }" class="list-group-item">
			    			<c:out value="${ document.documentName}" />
			    		</a>
			    	</c:forEach>
			    </div>
			</c:otherwise>
		</c:choose>
		
	</div>
</div>