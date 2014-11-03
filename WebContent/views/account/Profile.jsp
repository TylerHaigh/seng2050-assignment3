<%@page import="rgms.infrastructure.Session"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
	<c:when test="${empty profileUser}">
		<h1>Invalid User</h1>
		<p>No such user exists within this system</p>
	</c:when>
	
	<c:otherwise>
		<h1>
      <c:out value="${profileUser.fullName}" />
    </h1>
  	<div class="details row">
        <div class="image col-md-4">
            <img src="${ pageContext.request.contextPath }${ profileUser.imageReference }"
            	 alt="${ profileUser.fullName } Profile Image" />
        </div>
        <div class="col-md-8 aboutMe">
            
            <div> Username: <c:out value="${ profileUser.userName }" /> </div>
            
            <c:if test="${ profileUser.userName eq userSession.user.userName }">
	            <div class = "btn-group">
			    	<a href="${pageContext.request.contextPath}/account/editProfile?userId=${ userSession.user.id }" >Edit Profile</a>
			    </div>
		    </c:if>
		    
        </div>
    </div>
    
    <h2>
    	<c:out value="${ profileUser.firstName }" />'s Groups
    </h2>
    
    <div class="list-group">
    	<c:forEach var="group"
    		items="${ profileUserGroups }" >
    		<a href="ResearchGroup.jsp?groupName=${ group.groupName }" class="list-group-item">
    			<c:out value="${ group.groupName }" />
    		</a>
    	</c:forEach>
    </div>
      
	</c:otherwise>
</c:choose>