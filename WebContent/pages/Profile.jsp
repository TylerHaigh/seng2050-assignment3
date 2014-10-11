<%-- 
    Document   : Profile
    Created on : 29/09/2014, 7:37:31 PM
    Author     : Tyler 2
--%>

<%@page import="rgms.infrastructure.Session"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${empty userSession}">
	<c:redirect url="Login.jsp" />
</c:if>

<jsp:useBean id="profileController" class="rgms.controller.ProfileController" scope="page" />
<c:set var="profileUser" value="${ profileController.getProfileUser(param.userId) }" />

<!DOCTYPE html>
<html>
    <head>
        <title>
        	<c:choose>
        		<c:when test="${ not empty profileUser }">
        			<c:out value="${profileUser.fullName}" /> | RGMS
        		</c:when>
        		<c:otherwise>Invalid User | RGMS</c:otherwise>
        	</c:choose>
        </title>
        
        <link rel="stylesheet" type="text/css" href="../References/Styles.css" />
        <link rel="stylesheet" type="text/css" href="../References/bootstrap/css/bootstrap.css" />
        <link rel="stylesheet" type="text/css" href="../References/bootstrap/css/bootstrap-theme.css" />
        <script src="../References/jquery.js"></script> 
        <script src="../References/Scripts.js"></script> 
    </head>
    <body>
        <div class="wrapper">
            <div class="header">
            	<div class="headerNav"></div>
            </div>
            
            <div class="main">
            	
            	<c:choose>
	            	<c:when test="${empty profileUser}">
						<h1>Invalid User</h1>
						<p>No such user exists within this system</p>
					</c:when>
					
					<c:otherwise>
						<h1><c:out value="${profileUser.fullName}"></c:out></h1>
            	
		            	<div class="details">
			                <div id="image">
			                    <img src="${ profileUser.imageReference }"
			                    	 alt="${ profileUser.fullName } Profile Image" />
			                </div>
			                
			                <div id="aboutMe">
			                    <p>Details</p>
			                    <p>Details</p>
			                </div>
			            </div>
			            
			            <div class="researchGroups">
			            	
			            </div>
	            
					</c:otherwise>
				</c:choose>
            	
            </div>
            
            <div class="footer">
            	<p>Copyright The Aggregates 3.0 (2014)</p>
            </div>
            
        </div>
    </body>
</html>
