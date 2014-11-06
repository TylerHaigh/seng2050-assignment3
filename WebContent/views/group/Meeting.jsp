<!-- <%@page contentType="text/html" pageEncoding="UTF-8"%> -->
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h1><c:out value="${ meeting.description }" /></h1>

<div class="row">
	<div class="col-md-6">
		<h3><c:out value="Meeting Date: ${ meeting.dateDue }" /></h3>
	</div>
	<div class="col-md-6">
		<c:if test="${ userSession.user.id eq meeting.createdByUserId }">
			<a href="${ pageContext.request.contextPath }/group/deleteMeeting?meetingId=${ meeting.id}">
				Delete Meeting
			</a>
		</c:if>
	</div>
</div>


<hr />

<h3>Invited Users</h3>
<div class="list-group">
   	<c:forEach var="user" items="${ meetingUsers }" >
   		<a href="${ pageContext.request.contextPath }/account/profile?userId=${ user.id }"
   		   class="list-group-item">
   			<c:out value="${ user.fullName }" />
   		</a>
   	</c:forEach>
</div>