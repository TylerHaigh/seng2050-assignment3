package rgms.controller;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.util.logging.*;

import rgms.mvc.*;
import rgms.infrastructure.*;
import rgms.model.User;
import rgms.datacontext.UserManager;

import java.util.*;

@WebServlet(urlPatterns = { "/account/*", "/account" })
public class AccountController extends Controller {

  public AccountController() { }

  public void loginAction(HttpServletRequest req, HttpServletResponse res) {
	
	Map<String, String> viewData = new HashMap<String, String>();
    viewData.put("title", "Login");

    //Check the Request method
    if (req.getMethod() == HttpMethod.Get) {
      view(req, res, "/views/account/Login.jsp", viewData);
    } else if (req.getMethod() == HttpMethod.Post) {
      
      //User is trying to log in
      String userName = req.getParameter("userName");
      String password = req.getParameter("password");
      //boolean rememberMe = Boolean.parse(req.getParameter("rememberMe"));

      //Create a userSession for the user
      Session userSession = AuthenticationManager.login(userName, password, false);
      if (userSession == null) {
        req.setAttribute("loginError", true);
        view(req, res, "/views/account/Login.jsp", viewData);
      } else {
        HttpSession session = req.getSession();
        session.setAttribute("userSession", userSession);
        redirectToLocal(req, res, "/home/dashboard");
      }
      
    }
  }

  public void registerAction(HttpServletRequest req, HttpServletResponse res) {
    Map<String, String> viewData = new HashMap<String, String>();
    viewData.put("title", "Register");

    if (req.getMethod() == HttpMethod.Get) {
      view(req, res, "/views/account/Register.jsp", viewData);
    }
    else if (req.getMethod() == HttpMethod.Post) {
      User user = new User();
      user.setUserName(req.getParameter("userName"));
      user.setFirstName(req.getParameter("firstName"));
      user.setLastName(req.getParameter("lastName"));
      user.setStudentId(req.getParameter("studentId"));

      UserManager userManager = new UserManager();
      userManager.createUser(user, req.getParameter("password"));
      
      Session userSession = AuthenticationManager.login(user.getUserName(), req.getParameter("password") , false);
      if (userSession == null) {
        req.setAttribute("registerError", true);
        view(req, res, "/views/account/Register.jsp", viewData);
      }
      else {
        HttpSession session = req.getSession();
        session.setAttribute("userSession", userSession);
        redirectToLocal(req, res, "/account/profile?userId=" + user.getUserName());
      }
    }
  }

  public void profileAction(HttpServletRequest req, HttpServletResponse res) {
    Map<String, String> viewData = new HashMap<String, String>();
    viewData.put("title", "Profile");

	  String username = req.getParameter("userId");
	  Logger.getLogger("").info("Showing profile for user: " + username);
	  view(req, res, "/views/account/Profile.jsp", viewData);
  }
  //#########JOSH DID THIS##########
  public void editprofileAction(HttpServletRequest req, HttpServletResponse res) {
	  Map<String, String> viewData = new HashMap<String, String>();
	    viewData.put("title", "Edit Profile");
	    
	  String username = req.getParameter("userId");
	  Logger.getLogger("").info("Edit profile of: " + username);
	  view(req, res, "/views/account/EditProfile.jsp", viewData);
  }
  
  public void updateAction(HttpServletRequest req, HttpServletResponse res){
	  Map<String, String> viewData = new HashMap<String, String>();
	    viewData.put("title", "Update Profile");
	    String username = req.getParameter("userId");
		  Logger.getLogger("").info("Updating profile of: " + username);
	    if (req.getMethod() == HttpMethod.Get) {
	        view(req, res, "/views/account/EditProfile.jsp", viewData);
	      }
	      else if (req.getMethod() == HttpMethod.Post) {
	        User user = new User();
	        user.setUserName(req.getParameter("userName"));
	        user.setFirstName(req.getParameter("firstName"));
	        user.setLastName(req.getParameter("lastName"));

	        UserManager userManager = new UserManager();
	        userManager.updateUser(user, req.getParameter("password"));
	        
	        Session userSession = AuthenticationManager.login(user.getUserName(), req.getParameter("password") , false);
	        if (userSession == null) {
	          req.setAttribute("updateError", true);
	          view(req, res, "/views/account/Login.jsp", viewData);
	        }
	        else {
	          HttpSession session = req.getSession();
	          session.setAttribute("userSession", userSession);
	          redirectToLocal(req, res, "/account/profile?userId=" + username);
	          return;
	        }
	      } 
	    
	    
		  //view(req, res, "/views/account/Profile.jsp", viewData);
	    
  }

	public void logoutAction(HttpServletRequest req, HttpServletResponse res) {
		 Map<String, String> viewData = new HashMap<String, String>();
		 viewData.put("title", "Login");
		 Logger.getLogger("").info("Loging out");
		 HttpSession session = req.getSession();
	     session.removeAttribute("userSession");
	     view(req, res, "/views/account/Login.jsp", viewData);
		 return;
	}

  //################################
}