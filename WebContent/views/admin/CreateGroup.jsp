<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Create Group</h1>

<form role="form" action="${pageContext.request.contextPath}/group/researchGroup" method="post" onsubmit="return validateGroup();">
	<div class="form-group">
		<label for="groupName">Group Name:</label>
		<input type="text" class="form-control" name="groupName" id="groupName" placeholder="My Group" />
		
		<label for="description">Description:</label>
		<input type="text" class="form-control" name="description" id="description" />
		
		<input type="hidden" name="createdByUserId" id="createdByUserId" value="${ userSession.user.id }"/>
	</div>
	
	<button class="btn btn-default" type="submit">Submit</button>
	<button class="btn btn-default" type="reset">Reset</button>
</form>