<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h1>Notifications</h1>

<div class="list-group">
	<c:set var="counter" value="0" />
	
	<c:choose>
	
		<c:when test="${ fn:length(notifications) gt 0 }">
		
			<c:forEach var="notification" items="${ notifications }" >
				<div class="list-group-item">
					<c:out value="${ notification.description}" />
					
					<c:choose>
					
						<c:when test="${ notification.registeringUserId gt 0}">
							<a href="${ pageContext.request.contextPath }/home/notifications/?activate=${ counter }">
								<button type="button" class="btn btn-default">Activate</button>
							</a>
						</c:when>
						
						<c:otherwise>
							<a href="${ pageContext.request.contextPath }/home/notifications/?dismiss=${ counter }">
								<button type="button" class="btn btn-default">Dismiss</button>
							</a>
						</c:otherwise>
					
					</c:choose>
					
					<c:set var="counter" value="${ counter + 1 }" />
				</div>
			</c:forEach>
	
		</c:when>
		<c:otherwise>
			You have no new notifications
		</c:otherwise>
	
	</c:choose>
	
	
</div>