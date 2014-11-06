function validateReg(code)
{
	var valid = true;
	var x = document.getElementById('userName').value;
	var msg = "";
	if(x=="" || x== null)
	{
		msg = msg + "Your UserName was left Blank. \n";
		valid = false;
	}
	x = document.getElementById('studentId').value;
	if(x=="" || x== null)
	{
		msg = msg + "Your student Id was left blank \n";
		valid = false;
	}
	x = document.getElementById("firstName").value;
	if(x=="" || x== null)
	{
		msg = msg + "Your first name was left blank. \n"
		valid = false;
	}
	x = document.getElementById("lastName").value;
	if(x=="" || x== null)
	{
		msg = msg + "Your last name was left blank. \n"
		valid = false;
	}
	x = document.getElementById("password").value;
	if(x=="" || x== null)
	{
		msg = msg + "Your password was left blank. \n"
		valid = false;
	}
	y = document.getElementById("confirmPassword").value;
	if(y=="" || y== null)
	{
		msg = msg + "You must confirm your password. \n"
		valid = false;
	}
	if(y != x)
	{
		msg = msg + "Your passwords did not match. \n"
		valid = false;
	}	
	if(valid == false)
	{
		alert(msg);
	}
	return valid;
}

function validateEdit(code)
{
	var valid = true;
	var msg = "";
	x = document.getElementById("firstName").value;
	if(x=="" || x== null)
	{
		msg = msg + "Your first name was left blank. \n"
		valid = false;
	}
	x = document.getElementById("lastName").value;
	if(x=="" || x== null)
	{
		msg = msg + "Your last name was left blank. \n"
		valid = false;
	}
	x = document.getElementById("password").value;
	if(x=="" || x== null)
	{
		msg = msg + "Your password was left blank. \n"
		valid = false;
	}
	y = document.getElementById("confirmPassword").value;
	if(y=="" || y== null)
	{
		msg = msg + "You must confirm your password. \n"
		valid = false;
	}
	if(y != x)
	{
		msg = msg + "Your passwords did not match. \n"
		valid = false;
	}	
	if(valid == false)
	{
		alert(msg);
	}
	return valid;
}

function validateMeeting(code) {
	
	var valid = true;
	var msg = "";
	
	x = document.getElementById("description").value;
	if (x =="" || x == null) {
		msg += "Meeting Description was left blank. \n";
		valid = false;
	}
	
	x = document.getElementById("createdByUserId").value;
	if (x =="" || x == null) {
		msg += "Created by User Id was left blank. \n";
		valid = false;
	}
	
	x = document.getElementById("groupId").value;
	if (x =="" || x == null) {
		msg += "Group was left blank. \n";
		valid = false;
	}
	
	x = document.getElementById("datepicker").value;
	if (x =="" || x == null) {
		msg += "Meeting Date was left blank. \n";
		valid = false;
	}
	
	//Validate Meeting Date
	var validDate = true;
	var endMonth = x.indexOf("/");
	var endDay = x.indexOf("/", endMonth + 1);
	
	if (endMonth == -1 || endDay == -1) {
		validDate = false;
	} else {
		var month = parseInt(x.substring(0, endMonth));
		var day = parseInt(x.substring(endMonth + 1, endDay));
		var year = parseInt(x.substring(endDay + 1, x.length));
		
		if (isNaN(month) || isNaN(day) || isNaN(year)) {
			validDate = false;
		} else if (month < 0 || day < 0 || year < 0) {
			validDate = false
		} else if (month > 12 || year > 999999) {
			validDate = false
		} else {
			if (month == 1 || month == 3 || month == 5 || month == 7 || month == 10 || month == 12) {
				if (day > 31) {
					validDate = false;
				}
			} else if (month == 4 || month == 6 || month == 9 || month == 11) {
				if (day > 30) {
					validDate = false;
				}
			} else if (month == 2 && (year % 4) == 0 && day > 29) {
				validDate = false;
			} else if (month == 2 && day > 28) {
				validDate = false;
			}
		}
	}
	
	if (validDate == false) {
		msg += "Meeting Date format is invalid. Expected format: MM/dd/yyyy. \n";
		valid = false;
	}
	
	x = document.getElementById("meetingTime").value;
	if (x =="" || x == null) {
		msg += "Meeting Time was left blank. \n";
		valid = false;
	}
	
	//Validate Meeting Time
	var validTime = true;
	var colonIndex = x.indexOf(":");
	
	if (colonIndex == -1) {
		validTime = false;
	} else {
		var hours = parseInt(x.substring(0, colonIndex));
		var minutes = parseInt(x.substring(colonIndex + 1, x.length));
		
		if (isNaN(hours) || isNaN(minutes)) {
			validTime = false;
		} else if (hours < 0 || minutes < 0) {
			validTime = false;
		} else if (hours > 23 || minutes > 59) {
			validTime = false;
		}
	}
	
	if (validTime == false) {
		msg += "Meeting Time format is invalid. Expected format: HH:mm. \n";
		valid = false;
	}
		
	if (valid == false) {
		alert(msg);
	}
	
	return valid;
}