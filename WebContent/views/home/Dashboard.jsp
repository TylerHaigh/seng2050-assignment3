<%@page import="rgms.infrastructure.Session"%>
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
		
		<div class="list-group">
	    	<c:forEach var="meeting" items="${ userSession.user.meetings }" >
	    		<a href="#" class="list-group-item">
	    			<c:out value="${ meeting.description } : " />
	    			<c:out value="${ meeting.dateDue }" />
	    		</a>
	    	</c:forEach>
	    </div>
	</div>
	
</div>

<!-- Second row of Dashboard -->
<div class="row">
	<div class="col-md-6">
		<h2>My Groups</h2>
		
		<div class="list-group">
	    	<c:forEach var="group" items="${ userSession.user.groups }" >
	    		<a href="${pageContext.request.contextPath}/group/researchGroup?groupId=${group.id }" class="list-group-item">
	    			<c:out value="${ group.groupName }" />
	    			<span class="badge">10</span>
	    		</a>
	    	</c:forEach>
	    </div>
	</div>
	
	<div class="col-md-6">
		<h2>My Documents</h2>
		
		<div class="list-group">
	    	<c:forEach var="document" items="${ userDocuments }" >
	    		<a href="#" class="list-group-item">
	    			<c:out value="Document Name" />
	    			<span class="badge">10</span>
	    		</a>
	    	</c:forEach>
	    </div>
	</div>
</div>