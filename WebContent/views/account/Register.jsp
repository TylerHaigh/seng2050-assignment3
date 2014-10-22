<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Research Group Management System</h1>

<div class="row">
  <form method="post" action="${pageContext.request.contextPath}/account/register" class="col-md-4" role="form">
      <h2>User Registration</h2>
      <div class="form-group">
        <label for="userName">Username: </label>
        <input type="email" name="userName" class="form-control" />
      </div>

      <div class="form-group">
        <label for="studentId">Student Id: </label>
        <input type="text" name="studentId" class="form-control">
      </div>

      <div class="form-group">
        <label for="firstName">First Name: </label>
        <input type="text" name="firstName" class="form-control">
      </div>

      <div class="form-group">
        <label for="lastName">Last Name: </label>
        <input type="text" name="lastName" class="form-control">
      </div>

      <div class="form-group">
        <label for="password">Password: </label>
        <input type="password" name="password" class="form-control" />
      </div>

      <div class="form-group">
        <label for="confirmPassword">Confirm Password: </label>
        <input type="password" name="confirmPassword" class="form-control">
      </div>
      
      <button class="btn btn-default" type="submit">Register</button>
  </form>
</div>
