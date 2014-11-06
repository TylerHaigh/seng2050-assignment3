<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Administration Tools</h1>

<div class="list-group">
	<a href="${pageContext.request.contextPath}/admin/createGroup" class="list-group-item">
		Create Research Group
	</a>
	
	<a href="${pageContext.request.contextPath}/admin/createMeeting" class="list-group-item">
		Create Meeting
	</a>
	
	<a href="${pageContext.request.contextPath}/admin/showUsers" class="list-group-item">
		Show all RGMS Users
	</a>
	
	<a href="${pageContext.request.contextPath}/admin/showGroups" class="list-group-item">
		Show all RGMS Groups
	</a>
</div>