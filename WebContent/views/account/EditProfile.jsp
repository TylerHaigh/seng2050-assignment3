<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h1>Research Group Management System</h1>

<h2>Edit your profile</h2>

<div class="row">        						
	<form method="post" onsubmit="return validateEdit();" action="${pageContext.request.contextPath}/account/update?userId=${ userSession.user.id }" enctype="multipart/form-data">
		
	    <div class="form-group">
	      <input type="hidden" name="imageReference" value="${profileUser.imageReference}"/>
	      <label for="avatar">Avatar: </label>
	      
	      <div class="form-controls">
	        <img class="profile-image" src="${pageContext.request.contextPath}/Uploads/images/${profileUser.imageReference}" data-src="holder.js/100x100/sky/text:No Image" alt="Profile Picture"/>
	        <input type="file" name="avatar" id="avatar"/>
	      </div>
	    </div>
	    
	    <div class="form-group">
	      <label for="userName">Username: </label>
	      <input type="email" name="userName" id="userName" class="form-control" value="${profileUser.userName}" readonly/>
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
	      <input type="text" name="description" id="description" class="form-control">
	    </div>
	    
	    <div class="btn-group">
	      <input type="submit" value="Submit" class="btn btn-default" />
	      <input type="reset" value="Reset" class="btn btn-default" />
	    </div>
	    
	</form>
</div>

<script src="${pageContext.request.contextPath}/References/holder.js" type="text/javascript"></script>