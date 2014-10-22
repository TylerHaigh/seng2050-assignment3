<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Research Group Management System</h1>

<c:if test="${loginError}">
    <div class="alert alert-danger">Error: Invalid username or password</div>
</c:if>

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