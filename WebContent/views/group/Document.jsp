<%@page import="rgms.infrastructure.Session"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Access Summary for ${ title }</h1>


<table class="table table-bordered table-hover">
	<tr><th>User</th><th>Date Accessed</th></tr>
	<c:forEach var="ar" items="${accessRecords }" >
		<tr><td>${ar.user.userName } </td><td> ${ar.dateAccessed }</td> </tr>	
	</c:forEach>
</table>

