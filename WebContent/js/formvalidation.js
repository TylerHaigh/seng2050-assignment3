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