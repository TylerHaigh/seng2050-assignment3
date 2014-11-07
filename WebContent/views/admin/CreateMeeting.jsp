<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Create Meeting</h1>

<form role="form" action="${pageContext.request.contextPath}/group/meeting" method="post" onsubmit="return validateMeeting();">
	<div class="form-group">
		<label for="description">Meeting Description:</label>
		<input type="text" class="form-control" name="description" id="description" placeholder="My Meeting" />
		
		<input type="hidden" name="createdByUserId" id="createdByUserId" value="${ userSession.user.id }"/>
	</div>
	
	<div class="form-group">
		<label for="groupId">Group:</label>
		<select class="form-control" name="groupId" id="groupId" >
			<c:forEach var="group" items="${ groups }">
				<option value=${ group.id }>
					<c:out value="${ group.groupName }" />
				</option>
			</c:forEach>
		</select>
	</div>
	
	<div class="form-group">
		<label for="datepicker">Meeting Date:</label>
		<input type="text" class="form-control" name="datepicker" id="datepicker" />
		
		<label for="meetingTime">Meeting Time:</label>
		<input type="text" class="form-control" id="meetingTime" name="meetingTime" />
	</div>
	
	<button class="btn btn-default" type="submit">Submit</button>
	<button class="btn btn-default" type="reset">Reset</button>
</form>