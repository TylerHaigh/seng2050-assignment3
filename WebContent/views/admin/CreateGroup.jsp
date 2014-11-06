<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/formvalidation.js"/></script>

<h1>Create Meeting</h1>

<form role="form" action="${pageContext.request.contextPath}/group/researchGroup" method="post" onsubmit="return validateGroup();">
	<div class="form-group">
		<label for="groupName">Group Name:</label>
		<input type="text" class="form-control" name="groupName" id="groupname" placeholder="My Group" />
		
		<label for="groupName">Description:</label>
		<input type="text" class="form-control" name="description" id="description" />
		
		<input type="hidden" name="createdByUserId" id="createdByUserId" value="${ userSession.user.id }"/>
	</div>
	
	<button class="btn btn-default" type="submit">Submit</button>
	<button class="btn btn-default" type="reset">Reset</button>
</form>