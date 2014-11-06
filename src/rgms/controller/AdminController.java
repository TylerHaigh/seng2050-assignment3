package rgms.controller;

import java.util.*;

import javax.servlet.annotation.*;
import javax.servlet.http.*;

import rgms.datacontext.GroupManager;
import rgms.datacontext.UserManager;
import rgms.infrastructure.Session;
import rgms.model.*;
import rgms.mvc.Controller;

/**
 * Servlet to handle requests for administrator specific tasks. Manages creation
 * of meetings and groups
 * 
 * @author Tyler Haigh - C3182929
 * @author Simon Hartcher - C3185790
 * @author Josh Crompton - C3165877
 *
 */
@WebServlet(urlPatterns = { "/admin/*", "/admin"})
public class AdminController extends Controller {

	/**
	 * Constructor for the Admin controller. Provides no functionality
	 */
	public AdminController() { }
	
	/**
	 * Displays the admin tools page
	 * 
	 * - Requires a cookie for the session user
	 * 
	 * @param req The HTTP Request
	 * @param res The HTTP Response
	 */
	public void adminAction(HttpServletRequest req, HttpServletResponse res) {
		//Ensure there is a cookie for the session user
		if (AccountController.redirectIfNoCookie(req, res)) return;
		
		Map<String, Object> viewData = new HashMap<String, Object>();
		viewData.put("title", "Admin");
		  
		view(req, res, "/views/admin/Admin.jsp", viewData);
	}
	
	/**
	 * Displays the Create Meeting page
	 * 
	 * - Requires a cookie for the session user
	 * - Uses datepicker and timepicker
	 * 
	 * @param req The HTTP Request
	 * @param res The HTTP Response
	 */
	public void createmeetingAction(HttpServletRequest req, HttpServletResponse res) {
		//Ensure there is a cookie for the session user
		if (AccountController.redirectIfNoCookie(req, res)) return;
		
		Map<String, Object> viewData = new HashMap<String, Object>();
		viewData.put("title", "Create Meeting");
		  
		//Get the session user
		HttpSession session = req.getSession();
		Session adminSession = (Session) session.getAttribute("userSession");
		User admin = adminSession.getUser();
		
		//Get the groups
		GroupManager groupMan = new GroupManager();
		List<Group> groups = groupMan.getAllGroups(admin.getId());
		viewData.put("groups", groups);
		
		//DatePicker.js and TimePicker.js
		String dateTimePickerScript =
			"<script>" +
				"$(function() {" +
					"$(\"#datepicker\").datepicker();" +
					"$(\'#meetingTime\').timepicker({ 'timeFormat': 'H:i' });" +
				"});" +
			"</script>\n";
		
		String jqueryTimePickerScript =
			"<script type=\"text/javascript\" src=\"" + 
			req.getContextPath() + "/References/jquery-timepicker.js\"/></script>";
		
		String jqueryTimePickerStyle = "<link rel=\"stylesheet\" type=\"text/css\"" +
			"href=\"" + req.getContextPath() + "/References/jquery-timepicker.css\" />";
		
		//Put scripts into the view data to be rendered in the layout
		viewData.put("scripts", dateTimePickerScript + jqueryTimePickerScript);
		viewData.put("styles", jqueryTimePickerStyle);
		
		view(req, res, "/views/admin/CreateMeeting.jsp", viewData);
	}
	
	/**
	 * Displays the Create Group page
	 * 
	 * - Requires a cookie for the session user
	 * 
	 * @param req The HTTP Request
	 * @param res The HTTP Response
	 */
	public void creategroupAction(HttpServletRequest req, HttpServletResponse res) {
		//Ensure there is a cookie for the user
		if (AccountController.redirectIfNoCookie(req, res)) return;
		
		Map<String, Object> viewData = new HashMap<String, Object>();
		viewData.put("title", "Create Group");
		view(req, res, "/views/admin/CreateGroup.jsp", viewData);
	}

	/**
	 * Displays a page showing details for all users in RGMS
	 * 
	 * - Requires a cookie for the session user
	 * 
	 * @param req The HTTP Request
	 * @param res The HTTP Response
	 */
	public void showusersAction(HttpServletRequest req, HttpServletResponse res) {
		//Ensure there is a cookie for the session user
		if (AccountController.redirectIfNoCookie(req, res)) return;
		
		Map<String, Object> viewData = new HashMap<String, Object>();
		viewData.put("title", "RGMS Users");
		  
		//Get all users in RGMS database
		UserManager userMan = new UserManager();
		List<User> users = userMan.getEveryUser();
		viewData.put("allUsers", users);
		
		view(req, res, "/views/admin/ShowUsers.jsp", viewData);
	}
	
	/**
	 * Displays a page showing details for all groups in RGMS 
	 * 
	 * @param req The HTTP Request
	 * @param res The HTTP Response
	 */
	public void showgroupsAction(HttpServletRequest req, HttpServletResponse res) {
		//Ensure there is a cookie for the session user
		if (AccountController.redirectIfNoCookie(req, res)) return;
		
		Map<String, Object> viewData = new HashMap<String, Object>();
		viewData.put("title", "RGMS Groups");
		  
		//Get all groups in the RGMS database
		GroupManager groupMan = new GroupManager();
		List<Group> groups = groupMan.getEveryGroup();
		viewData.put("allGroups", groups);
		
		view(req, res, "/views/admin/ShowGroups.jsp", viewData);
	}
}
