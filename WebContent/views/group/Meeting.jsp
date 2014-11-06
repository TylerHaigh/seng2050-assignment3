<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h1><c:out value="${ meeting.description }" /></h1>
<h3><c:out value="Meeting Date: ${ meeting.dateDue }" /></h3>

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