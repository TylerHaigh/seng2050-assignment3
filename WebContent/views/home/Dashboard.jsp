<%@page import="rgms.infrastructure.Session"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Dashboard</h1>

<!-- First row of Dashboard -->
<div class="row">
	<div class="col-md-6">
		<h2>My Groups</h2>
		
		<div class="list-group">
	    	<c:forEach var="group" items="${ userGroups }" >
	    		<a href="ResearchGroup.jsp?groupName=${ group.groupName }" class="list-group-item">
	    			<c:out value="${ group.groupName }" />
	    			<span class="badge">10</span>
	    		</a>
	    	</c:forEach>
	    </div>
	</div>
	
	<div class="col-md-6">
		<h2>Meetings</h2>
		
		<div class="list-group">
	    	<c:forEach var="meeting" items="${ userMeetings }" >
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