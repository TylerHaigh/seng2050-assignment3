<%@page import="rgms.infrastructure.Session"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<nav class="navbar">
    <div class="navbar-header">
        <img src="${pageContext.request.contextPath}/References/images/UoN_Logo.png" alt="UoN Logo" class="pull-left"/>
    </div>
    
    <c:if test="${not empty userSession}">
        <ul id="target" class="nav nav-pills">
            <li><a href="${pageContext.request.contextPath}/home/dashboard/">Home</a></li>
            
            <li class="dropdown">
        		<a href="#" class="dropdown-toggle" data-toggle="dropdown" >Research Groups <span class="caret"></span>
        		</a>
        		<ul class="dropdown-menu">
                    
                    <c:forEach var="group" items="${ userSession.user.groups }" >
			    		<li>
				    		<a href="#">
				    			<c:out value="${ group.groupName }" />
				    		</a>
			    		</li>
			    	</c:forEach>
			    	
                </ul>
            </li>

            <li><a href="#">Discussion Board</a></li>
            
            <li class="dropdown">
            	<a href="#" class="dropdown-toggle" data-toggle="dropdown" >
                    <c:out value="${userSession.user.fullName}" />
                    <span class="caret"></span>
                </a>
                
                <ul class="dropdown-menu" >
                    <li><a href="${pageContext.request.contextPath}/account/profile?userId=${ userSession.user.userName }">View Profile</a></li>
                    
                    <c:if test="${ userSession.user.admin == true }">
		                <li><a href="${pageContext.request.contextPath}/admin/admin">Admin Tools</a></li>
	                </c:if>
                    
                    <li><a href="${pageContext.request.contextPath}/account/logout">Log Out</a></li>
                </ul>
            </li>
        </ul>
    </c:if>
</nav>
