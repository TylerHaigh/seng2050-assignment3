<%-- 
    Document   : EditProfile
    Created on : 05/10/2014, 4:10:48 PM
    Author     : Tyler 2
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="profileController" class="rgms.controller.ProfileController" scope="page" />
<c:set var="profileUser" value="${ profileController.getProfileUser(param.userId) }" />
<c:if test="${empty userSession}">
	<c:redirect url="Login.jsp" />
</c:if>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/formvalidation.js"/></script>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

 
    <body>
        <h1>Research Group Management System</h1>
        <!--Form for user to update profile with. Will have to discuss how it is updated in DB. and JavaScript client side validation-->
<div class="row">        						<!-- Not sure about sending the userId here but when it redirects back to the profile I lost the profile -->
	<form method="post" onsubmit="return validateEdit();" action="${pageContext.request.contextPath}/account/update?userId=${ userSession.user.userName }">
		<h2>Edit your profile</h2>
      <div class="form-group">
        <label for="userName">Username: </label>
        <input type="email" name="userName" class="form-control" value="${profileUser.userName}" readonly/>
      </div>

      <div class="form-group">
        <label for="firstName">First Name: </label>
        <input type="text" name="firstName" id="firstName" class="form-control" value="${profileUser.firstName }">
      </div>

      <div class="form-group">
        <label for="lastName">Last Name: </label>
        <input type="text" name="lastName" id="lastName" class="form-control" value="${profileUser.lastName }" >
      </div>

      <div class="form-group">
        <label for="password">Password: </label>
        <input type="password" name="password" id="password" class="form-control" />
      </div>

      <div class="form-group">
        <label for="confirmPassword">Confirm Password: </label>
        <input type="password" name="confirmPassword" id="confirmPassword" class="form-control">
      </div>

	<div class="form-group">
        <label for="description">Description: </label>
        <input type="text" name="description" class="form-control">
      </div>
	   	<input type = "submit" value="Submit" />
	   	<input type="reset" value="Reset" />
        	
        
	</form>
 </div>

