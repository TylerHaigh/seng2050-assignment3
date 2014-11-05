<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Research Group Management System</h1>

<c:choose>

	<c:when test="${loginError}">
		<div class="alert alert-danger">Error: Invalid username or password</div>
	</c:when>
	
	<c:when test="${registerSuccess}">
		<div class="alert alert-success">Success: Your account has been registered. 
    	Please wait for a Coordinator to approve your access</div>
	</c:when>
	
	<c:when test="${inactiveUser}">
		<div class="alert alert-danger">Sorry, your account is inactive. 
    	Please try again later</div>
	</c:when>

</c:choose>

<form method="post" class="login-form">
    <h2>${title}</h2>
    
    <div class="input-group">
    	<label for="userName">Username: </label>
    	<input type="text" name="userName" class="form-control" />
    </div>
    
    <br />
    
    <div class="input-group">
    	<label for="password">Password: </label>
    	<input type="password" name="password" class="form-control" />
    </div>
    
    <br />
    
    <div class="btn-group">
    	<input type="submit" value="Login" class="btn btn-default"  />
        <a href="${pageContext.request.contextPath}/account/register/" class="btn btn-default">Register</a>
    </div>
</form>