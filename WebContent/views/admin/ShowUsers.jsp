<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>RGMS Users</h1>

<table class="table table-bordered table-hover">
	<tr>
		<th>#</th>
		<th>First Name</th>
		<th>Last Name</th>
		<th>Username</th>
		<th>Student Id</th>
		<th>Administrator</th>
		<th>Active</th>
	</tr>
	<c:forEach var="user" items="${ allUsers }" >
		<tr>
			<td>
				<a href="${ pageContext.request.contextPath }/account/profile?userId=${ user.id }" >
					<c:out value="${ user.id }" />
				</a>
			</td>
			<td><c:out value="${ user.firstName }" /></td>
			<td><c:out value="${ user.lastName }" /></td>
			<td><c:out value="${ user.userName }" /></td>
			<td><c:out value="${ user.studentId }" /></td>
			<td>
				<c:choose>
					<c:when test="${ user.admin }" >
						<span class="glyphicon glyphicon-ok"></span>
					</c:when>
					<c:otherwise>
						<span class="glyphicon glyphicon-remove"></span>
					</c:otherwise>
				</c:choose>
			</td>
			<td>
				<c:choose>
					<c:when test="${ user.active }" >
						<span class="glyphicon glyphicon-ok"></span>
					</c:when>
					<c:otherwise>
						<span class="glyphicon glyphicon-remove"></span>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
  	</c:forEach>

</table>