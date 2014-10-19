<%-- 
    Document   : EditProfile
    Created on : 05/10/2014, 4:10:48 PM
    Author     : Tyler 2
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${empty userSession}">
	<c:redirect url="Login.jsp" />
</c:if>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Your Profile</title>
    </head>
    <body>
        <h1>Edit your profile</h1>
        <!--Form for user to update profile with. Will have to discuss how it is updated in DB. and JavaScript client side validation-->
        <form action="">
        <!-- Table to line everything up -->
        	<table id="editProfile">
        		<!-- What data shall the user be able to change. Research fields? -->
        		<tr><td><label for="rfields"></label></td>
        			<td><input type="text" name="rfields" id="rfields" /></td>
       			</tr>
        	</table>
        	<input type = "submit" value="Submit" />
        	<input type="reset" value="Reset" />
        	
        
        </form>
    </body>
</html>
