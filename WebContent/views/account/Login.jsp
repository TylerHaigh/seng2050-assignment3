<%-- 
    Document   : Login
    Created on : 30/09/2014, 7:23:59 PM
    Author     : Tyler 2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%-- 
<jsp:useBean id="loginController" class="rgms.controller.LoginController" scope="page" />
<jsp:setProperty name="loginController" property="session" value="${ pageContext.session }"/>
<c:set var="autoLogin" value="${ loginController.autoLogin() }" />
<c:choose>
	<c:when test="${ empty autoLogin }">
		<c:set var="username" value="${ param.username }" />
		<c:set var="password" value="${ param.password }" />
		<c:set var="error" value="${ param.error }" />
		
		<c:if test="${ not empty username and not empty password }" >
			<c:redirect url="${ loginController.login(username, password) }" />
		</c:if>
	</c:when>
	
	<c:otherwise>
		<c:redirect url="${ autoLogin }" />
	</c:otherwise>
</c:choose>
--%>

<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Login | RGMS</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/References/Styles.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/References/bootstrap/css/bootstrap.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/References/bootstrap/css/bootstrap-theme.css" />
    </head>
    <body>
        <div class="wrapper">
            
            <div class="header">
                <img src="${pageContext.request.contextPath}/References/images/UoN_Logo.png" alt="UoN Logo"/>
            </div>
            
            <div class="main">
                
                <h1>Research Group Management System</h1>
                
                <c:if test="${error}">
                <div class="alert alert-danger">Error: Invalid username or password</div>
                </c:if>
                
                <form method="post" class="login-form">
                    
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
            </div>
            
            <div class="footer">
                <p>Copyright The Aggregates 3.0 (2014)</p>
            </div>
            
        </div>
    </body>
</html>
