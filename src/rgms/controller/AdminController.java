package rgms.controller;

import java.util.*;

import javax.servlet.annotation.*;
import javax.servlet.http.*;

import rgms.datacontext.GroupManager;
import rgms.datacontext.UserManager;
import rgms.infrastructure.Session;
import rgms.model.*;
import rgms.mvc.Controller;

@WebServlet(urlPatterns = { "/admin/*", "/admin"})
public class AdminController extends Controller {

	public AdminController() { }
	
	public void adminAction(HttpServletRequest req, HttpServletResponse res) {
		if (AccountController.redirectIfNoCookie(req, res)) return;
		
		Map<String, Object> viewData = new HashMap<String, Object>();
		viewData.put("title", "Admin");
		  
		view(req, res, "/views/admin/Admin.jsp", viewData);
	}
	
	public void createmeetingAction(HttpServletRequest req, HttpServletResponse res) {
		if (AccountController.redirectIfNoCookie(req, res)) return;
		
		Map<String, Object> viewData = new HashMap<String, Object>();
		viewData.put("title", "Create Meeting");
		  
		HttpSession session = req.getSession();
		Session adminSession = (Session) session.getAttribute("userSession");
		User admin = adminSession.getUser();
		
		//Get the groups
		GroupManager groupMan = new GroupManager();
		List<Group> groups = groupMan.getAllGroups(admin.getId());
		viewData.put("groups", groups);
		
		//DatePicker.js
		String datePickerScript =
			"<script>" +
				"$(function() {" +
					"$(\"#datepicker\").datepicker();" +
				"});" +
			"</script>";
		viewData.put("scripts", datePickerScript);
		
		view(req, res, "/views/admin/CreateMeeting.jsp", viewData);
	}
	
	public void creategroupAction(HttpServletRequest req, HttpServletResponse res) {
		if (AccountController.redirectIfNoCookie(req, res)) return;
		
		Map<String, Object> viewData = new HashMap<String, Object>();
		viewData.put("title", "Create Group");
		view(req, res, "/views/admin/CreateGroup.jsp", viewData);
	}

	public void showusersAction(HttpServletRequest req, HttpServletResponse res) {
		if (AccountController.redirectIfNoCookie(req, res)) return;
		
		Map<String, Object> viewData = new HashMap<String, Object>();
		viewData.put("title", "RGMS Users");
		  
		UserManager userMan = new UserManager();
		List<User> users = userMan.getEveryUser();
		
		viewData.put("allUsers", users);
		
		view(req, res, "/views/admin/ShowUsers.jsp", viewData);
	}
	
	public void showgroupsAction(HttpServletRequest req, HttpServletResponse res) {
		if (AccountController.redirectIfNoCookie(req, res)) return;
		
		Map<String, Object> viewData = new HashMap<String, Object>();
		viewData.put("title", "RGMS Groups");
		  
		GroupManager groupMan = new GroupManager();
		List<Group> groups = groupMan.getEveryGroup();
		
		viewData.put("allGroups", groups);
		
		view(req, res, "/views/admin/ShowGroups.jsp", viewData);
	}
}
