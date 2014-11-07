<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>RGMS Groups</h1>

<table class="table table-bordered table-hover">
	<tr>
		<th>#</th>
		<th>Group Name</th>
		<th>Description</th>
	</tr>
	<c:forEach var="group" items="${ allGroups }" >
		<tr>
			<td>
				<a href="${ pageContext.request.contextPath }/group/researchGroup?groupId=${ group.id }" >
					<c:out value="${ group.id }" />
				</a>
			</td>
			<td><c:out value="${ group.groupName }" /></td>
			<td><c:out value="${ group.description }" /></td>
		</tr>
  	</c:forEach>

</table>