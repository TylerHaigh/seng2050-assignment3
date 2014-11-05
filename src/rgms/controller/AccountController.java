package rgms.controller;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.util.logging.*;

import rgms.mvc.*;
import rgms.infrastructure.*;
import rgms.model.*;
import rgms.datacontext.*;

import java.util.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;

@WebServlet(urlPatterns = { "/account/*", "/account" })
@MultipartConfig
public class AccountController extends Controller {
  private static Logger logger = Logger.getLogger("rgms.AccountController");

  public AccountController() { }

  public void loginAction(HttpServletRequest req, HttpServletResponse res) {
	
	Map<String, Object> viewData = new HashMap<String, Object>();
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
      } else if (!userSession.getUser().isActive()) {
    	  req.setAttribute("inactiveUser", true);
          view(req, res, "/views/account/Login.jsp", viewData);
      } else {
        HttpSession session = req.getSession();
        session.setAttribute("userSession", userSession);
        redirectToLocal(req, res, "/home/dashboard");
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
      
      //Try and authenticate the user
      Session userSession = AuthenticationManager.login(user.getUserName(), req.getParameter("password") , false);
      if (userSession == null) {
        req.setAttribute("registerError", true);
        view(req, res, "/views/account/Register.jsp", viewData);
      }
      else {
        
    	  //Notify all coordinators to approve the user
          NotificationManager notificationManager = new NotificationManager();
          User admin = userManager.get(1);
          
          Notification registerNotification = new Notification(admin.getId(), admin,
        		  NotificationType.RegisteringUser, user, "New user " + user.getFullName() + " wants to join");
          
          notificationManager.createNotification(registerNotification);
    	  
    	  //Redirect back to login page
    	  req.setAttribute("registerSuccess", true);
    	  redirectToLocal(req, res, "/account/login");
      }
    }
  }

  public void profileAction(HttpServletRequest req, HttpServletResponse res) {
	  Map<String, Object> viewData = new HashMap<String, Object>();
	  viewData.put("title", "Profile");

	  User profileUser = null;
	  List<Group> profileUserGroups = null;
	  
	  try {
		  UserManager um = new UserManager();
		  GroupManager gm = new GroupManager();
		  
		  int userId = Integer.parseInt(req.getParameter("userId"));
		  profileUser = um.get(userId);

		  if (profileUser == null) {
        //return 404
        httpNotFound(res);
        return;
		  } else {
			  Logger.getLogger("").info("Showing profile for user: " + profileUser.getFullName());
			  profileUserGroups = gm.getAllGroups(profileUser.getId());
		  }
		  
	  } catch (Exception e) {
		  Logger.getLogger("").log(Level.SEVERE, "An error occurred when getting profile user", e);
	  }
	  
	  viewData.put("profileUser", profileUser);
	  viewData.put("profileUserGroups", profileUserGroups);
	  view(req, res, "/views/account/Profile.jsp", viewData);
  }
  
  public void editprofileAction(HttpServletRequest req, HttpServletResponse res) {
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
      httpNotFound(res);
      return;
    }
  }
  
  public void updateAction(HttpServletRequest req, HttpServletResponse res){
	  Map<String, Object> viewData = new HashMap<String, Object>();
	    viewData.put("title", "Update Profile");

	    int userId = Integer.parseInt(req.getParameter("userId"));
		  Logger.getLogger("").info("Updating profile of: " + userId);
	    if (req.getMethod() == HttpMethod.Get) {
        view(req, res, "/views/account/EditProfile.jsp", viewData);
      }
      else if (req.getMethod() == HttpMethod.Post) {
        User user = new User();
        user.setUserName(req.getParameter("userName"));
        user.setFirstName(req.getParameter("firstName"));
        user.setLastName(req.getParameter("lastName"));

        try {
          Part avatar = req.getPart("avatar");
          if (avatar != null) {
            String imageRef = saveProfileImage(avatar);
            user.setImageReference(imageRef);
            logger.info("Avatar uploaded for " + userId);
          }
        }
        catch (Exception e) {
          logger.log(Level.SEVERE, "Error loading file", e);
        }

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
          redirectToLocal(req, res, "/account/profile?userId=" + userId);
          return;
        }
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
		 HttpSession session = req.getSession();
	     session.removeAttribute("userSession");
	     redirectToLocal(req, res, "/account/login");
		 return;
	}
}