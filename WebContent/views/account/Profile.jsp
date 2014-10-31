<%@page import="rgms.infrastructure.Session"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:useBean id="profileController" class="rgms.controller.ProfileController" scope="page" />
<c:set var="profileUser" value="${ profileController.getProfileUser(param.userId) }" />

<c:choose>
	<c:when test="${empty profileUser}">
		<h1>Invalid User</h1>
		<p>No such user exists within this system</p>
	</c:when>
	
	<c:otherwise>
		<h1>
      <c:out value="${profileUser.fullName}" />
    </h1>
  	<div class="details">
        <div id="image">
            <img src="${ profileUser.imageReference }"
            	 alt="${ profileUser.fullName } Profile Image" />
        </div>
        <div id="aboutMe">
            <p>Details</p>
            <p>Details</p>
            <div class = "btn-group">
    	<a href="${pageContext.request.contextPath}/account/editProfile?userId=${ userSession.user.userName }" >Edit Profile</a>
    </div>
        </div>
    </div>
    
    <h2>
    	<c:out value="${ profileUser.firstName }" />'s Groups
    </h2>
    
    <div class="list-group">
    	<c:forEach var="group"
    		items="${ profileController.getGroups(profileUser.studentId) }" >
    		<a href="ResearchGroup.jsp?groupName=${ group.groupName }" class="list-group-item">
    			<c:out value="${ group.groupName }" />
    		</a>
    	</c:forEach>
    </div>
      
	</c:otherwise>
</c:choose>