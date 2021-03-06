<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h1>Notifications</h1>

<c:choose>
	<c:when test="${ dismissed }">
		<div class="alert alert-success">Notification successfully dismissed</div>
	</c:when>
	
	<c:when test="${ activated }">
		<div class="alert alert-success">User account successfully activated</div>
	</c:when>
	
	<c:when test="${ added }">
		<div class="alert alert-success">User account added to Group</div>
	</c:when>
</c:choose>

<div class="list-group">
	<c:set var="counter" value="0" />
	
	<c:choose>
		
		<c:when test="${ fn:length(notifications) gt 0 }">
			<c:forEach var="notification" items="${ notifications }" >
				<div class="list-group-item">
					
					<c:choose>
						<c:when test="${ fn:contains(notification.link, 'activate') }">
							<c:out value="${ notification.description}" />
							
							<a href="${ pageContext.request.contextPath }${ notification.link }">
								<button type="button" class="btn btn-default">Activate</button>
							</a>
						</c:when>
						
						<c:when test="${ fn:contains(notification.link, 'addUserId') }">
							<c:out value="${ notification.description}" />
							
							<a href="${ pageContext.request.contextPath }${ notification.link }">
								<button type="button" class="btn btn-default">Add to Group</button>
							</a>
						</c:when>
						
						<c:otherwise>
							<a href="${ pageContext.request.contextPath }${ notification.link }">
								<c:out value="${ notification.description}" />
							</a>
							
							<a href="${ pageContext.request.contextPath }/home/notifications?dismiss=${ counter }">
								<button type="button" class="btn btn-default">Dismiss</button>
							</a>
						</c:otherwise>
					</c:choose>
					
				</div>
				
				<c:set var="counter" value="${ counter + 1 }" />
				
			</c:forEach>
		</c:when>
		
		<c:otherwise>
			You have no new notifications
		</c:otherwise>
	</c:choose>
	
</div>