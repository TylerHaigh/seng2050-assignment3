package rgms.controller;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.util.logging.*;

import rgms.mvc.*;
import rgms.infrastructure.*;
import rgms.model.*;
import rgms.datacontext.*;

import java.util.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;

@WebServlet(urlPatterns = { "/account/*", "/account" })
@MultipartConfig
public class AccountController extends Controller {
  private static Logger logger = Logger.getLogger("rgms.AccountController");

  public AccountController() { }

  public static boolean redirectIfNoCookie(HttpServletRequest req, HttpServletResponse res) {
	  Session userSession = null; 
	  
	  //Check if there is a cookie
	  Cookie[] cookies = req.getCookies();
	  if (cookies != null) {
		  for (Cookie cookie : cookies) {
			  if (cookie.getName().equals("userCookie")) {
				  
				  //Create the User
				  UserManager userMan = new UserManager();
				  int userId = Integer.parseInt(cookie.getValue());
				  User user = userMan.get(userId);
				  
				  //Create a session for the user
				  userSession = new Session(false, userId, user);
				  break;
			  }
		  }
	  }
	  
	  if (userSession != null) {
		  HttpSession session = req.getSession();
		  if (session.getAttribute("userSession") == null)
			  session.setAttribute("userSession", userSession);
		  
		  return false;
	  } else {
		  redirectToLocal(req, res, "/account/login");
		  return true;
	  }
  }
  
  public void loginAction(HttpServletRequest req, HttpServletResponse res) {
	
	Map<String, Object> viewData = new HashMap<String, Object>();
    viewData.put("title", "Login");

    //Check the Request method
    if (req.getMethod() == HttpMethod.Get) {
      
    	//Check if there is a registerSuccess session object
    	HttpSession session = req.getSession();
        Object registerSuccess = session.getAttribute("registerSuccess");
    	if (registerSuccess != null) {
    		req.setAttribute("registerSuccess", true);
    		session.removeAttribute("registerSuccess");
    	}
        
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
      } else if (!userSession.getUser().isActive()) {
    	  req.setAttribute("inactiveUser", true);
          view(req, res, "/views/account/Login.jsp", viewData);
      } else {
        
    	  //Make a cookie for the user session
    	  Cookie loginCookie = new Cookie("userCookie", String.valueOf(userSession.getUser().getId()));
    	  loginCookie.setMaxAge(24 * 60 * 60); // 24 Hours
    	  loginCookie.setPath("/");

    	  res.addCookie(loginCookie);
    	  
    	  redirectToLocal(req, res, "/home/dashboard");
    	  return;
      }
      
    }
  }

  public void registerAction(HttpServletRequest req, HttpServletResponse res) {
    Map<String, Object> viewData = new HashMap<String, Object>();
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

      //Create the user in the database
      UserManager userManager = new UserManager();
      userManager.createUser(user, req.getParameter("password"));
      
      //Update the User from the database
      user = userManager.get(user.getUserName());
      
      //Try and authenticate the user
      Session userSession = AuthenticationManager.login(user.getUserName(), req.getParameter("password") , false);
      if (userSession == null) {
        req.setAttribute("registerError", true);
        view(req, res, "/views/account/Register.jsp", viewData);
      }
      else {
        
    	  //Notify all coordinators to approve the user
          NotificationManager notificationManager = new NotificationManager();
          List<User> coordinators = userManager.getCoordinators();
          for (User coordinator : coordinators) {
        	  
        	  Notification registerNotification = new Notification(coordinator.getId(), coordinator,
            		  "New user " + user.getFullName() + " wants to join",
            		  "/home/notifications?activate=" + user.getId());
              
              notificationManager.createNotification(registerNotification);
          }
    	  
    	  //Redirect back to login page
          
          /*
           * This is a hack since you can't set attributes when redirecting - Tyler
           */
          HttpSession session = req.getSession();
          session.setAttribute("registerSuccess", true);
    	  redirectToLocal(req, res, "/account/login");
    	  return;
      }
    }
  }

  public void profileAction(HttpServletRequest req, HttpServletResponse res) {
	  
	  if (redirectIfNoCookie(req, res)) return;
	  
	  Map<String, Object> viewData = new HashMap<String, Object>();
	  viewData.put("title", "Profile");

	  User profileUser = null;
	  List<Group> profileUserGroups = null;
	  
	  try {
		  UserManager um = new UserManager();
		  GroupManager gm = new GroupManager();
		  //If finding user by ID
		  if(req.getParameter("userId") != null){
			  int userId = Integer.parseInt(req.getParameter("userId"));
			  profileUser = um.get(userId);
			  if(profileUser != null){
				  Logger.getLogger("").info("Showing profile for user: " + profileUser.getFullName());
				  profileUserGroups = gm.getAllGroups(profileUser.getId());
			  }
		  }
		  //Finding user by username
		  else if(req.getParameter("userName") != null){
			  String userName = req.getParameter("userName");
			  profileUser = um.get(userName);
		  }
		  if (profileUser == null) {
	        //return 404
	        httpNotFound(req, res);
	        return;
		  } 
		  /*else {
			  Logger.getLogger("").info("Showing profile for user: " + profileUser.getFullName());
			  profileUserGroups = gm.getAllGroups(profileUser.getId());
		  }*/
		  
	  } catch (Exception e) {
		  Logger.getLogger("").log(Level.SEVERE, "An error occurred when getting profile user", e);
	  }
	  
	  viewData.put("profileUser", profileUser);
	  viewData.put("profileUserGroups", profileUserGroups);
	  view(req, res, "/views/account/Profile.jsp", viewData);
  }
  
  public void editprofileAction(HttpServletRequest req, HttpServletResponse res) {
	  if (redirectIfNoCookie(req, res)) return;
	  
	  Map<String, Object> viewData = new HashMap<String, Object>();
	  viewData.put("title", "Edit Profile");

    if (req.getMethod() == HttpMethod.Get) {
      //get userid
  	  int userId = Integer.parseInt(req.getParameter("userId"));

      //get user
      UserManager um = new UserManager();
      User profileUser = um.get(userId);

      viewData.put("profileUser", profileUser);
  	  view(req, res, "/views/account/EditProfile.jsp", viewData);
    }
    else {
      httpNotFound(req, res);
      return;
    }
  }
  
  public void updateAction(HttpServletRequest req, HttpServletResponse res){
	  redirectIfNoCookie(req, res);
	  
	  Map<String, Object> viewData = new HashMap<String, Object>();
	    viewData.put("title", "Update Profile");

	    int userId = Integer.parseInt(req.getParameter("userId"));
		  Logger.getLogger("").info("Updating profile of: " + userId);
	    if (req.getMethod() == HttpMethod.Get) {
        view(req, res, "/views/account/EditProfile.jsp", viewData);
      }
      else if (req.getMethod() == HttpMethod.Post) {
        User user = new User();
        user.setId(userId);
        user.setUserName(req.getParameter("userName"));
        user.setFirstName(req.getParameter("firstName"));
        user.setLastName(req.getParameter("lastName"));
        user.setDescription(req.getParameter("description"));

        try {
          Part avatar = req.getPart("avatar");
          if (avatar.getSize() > 0) {
            String imageRef = saveProfileImage(avatar);
            user.setImageReference(imageRef);
            logger.info("Avatar uploaded for " + userId);
          }
          else {
            user.setImageReference(req.getParameter("imageReference"));
          }
        }
        catch (Exception e) {
          logger.log(Level.SEVERE, "Error loading file", e);
        }

        UserManager userManager = new UserManager();
        userManager.updateUser(user);
        
        redirectToLocal(req, res, "/account/profile?userId=" + userId);
        return;
      } 
  }

  private String saveProfileImage(Part profileImage) {
    try {
      //get random uuid
      String id = UUID.randomUUID().toString();

      //save to disk
      String savePath = getServletContext().getRealPath("/Uploads/images") + "/" + id;
      profileImage.write(savePath);

      return id;
    }
    catch (Exception e) {
      logger.log(Level.SEVERE, "Error saving profile image", e);
      return null;
    }
  }

  public void logoutAction(HttpServletRequest req, HttpServletResponse res) {
	 Map<String, Object> viewData = new HashMap<String, Object>();
	 viewData.put("title", "Login");
	 Logger.getLogger("").info("Loging out");
	 
	 //Find user cookie
	 Cookie[] cookies = req.getCookies();
	 Cookie loginCookie = null;
	 if (cookies != null) {
		 for (Cookie cookie : cookies) {
			 if (cookie.getName().equals("userCookie")) {
				 loginCookie = cookie;
				 break;
			 }
		 }
	 }
	 
	 //Remove Cookie
	 if (loginCookie != null) {
		 loginCookie.setMaxAge(0);
		 loginCookie.setPath("/");
		 res.addCookie(loginCookie);
	 }
			 
	 //Remove Session
	 HttpSession session = req.getSession();
     session.removeAttribute("userSession");
     
     redirectToLocal(req, res, "/account/login");
	 return;
  }

}