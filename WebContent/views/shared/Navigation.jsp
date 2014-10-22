<%@page import="rgms.infrastructure.Session"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<nav class="navbar">
    <div class="navbar-header">
        <img src="${pageContext.request.contextPath}/References/images/UoN_Logo.png" alt="UoN Logo" class="pull-left"/>
    </div>
    <c:if test="{not empty userSession}">
        <ul id="target" class="nav nav-pills">
            <li><a href="${pageContext.request.contextPath}/home/dashboard/">Home</a></li>
            
            <li class="dropdown">
        		<a href="#" class="dropdown-toggle" data-toggle="dropdown" >Research Groups <span class="caret"></span>
        		</a>
        		<ul class="dropdown-menu">
                    <li><a href="#" data-toggle="dropdown">Group 1</a></li>
                    <li><a href="#">Group 2</a></li>
                </ul>
            </li>

            <li><a href="#">Discussion Board</a></li>
            
            <li class="dropdown">
            	<a href="#" class="dropdown-toggle" data-toggle="dropdown" >
                    <c:out value="${userSession.user.fullName}" />
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu" >
                    <li><a href="../account/Profile.jsp?userId=${userSession.user.studentId}">View Profile</a></li>
                    <li><a href="Redirector.jsp?logout=true">Log Out</a></li>
                </ul>
            </li>
        </ul>
    </c:if>
</nav>