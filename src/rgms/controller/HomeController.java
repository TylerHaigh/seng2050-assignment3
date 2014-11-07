package rgms.controller;

import java.util.logging.*;
import java.util.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import rgms.datacontext.*;
import rgms.infrastructure.*;
import rgms.model.*;
import rgms.mvc.*;

/**
 * Servlet to handle requests for the home pages. Manages displaying of the
 * dashboard and handling of notifications
 * 
 * @author Tyler Haigh - C3182929
 * @author Simon Hartcher - C3185790
 * @author Josh Crompton - C3165877
 *
 */
@WebServlet(urlPatterns = { "/home/*", "/home" })
public class HomeController extends Controller {

	private static final Logger logger = Logger.getLogger(HomeController.class.getName());
	
	/**
	 * Constructor for a Home Controller. Provides no functionality
	 */
	public HomeController() { }
	
	/**
	 * Displays the dashboard for the session user
	 * 
	 * - Requires a cookie for the session user
	 * 
	 * @param req The HTTP Request
	 * @param res The HTTP Response
	 */
	public void dashboardAction(HttpServletRequest req, HttpServletResponse res) {
		//Ensure there is a cookie for the session user
		if (AccountController.redirectIfNoCookie(req, res)) return;
		
		Map<String, Object> viewData = new HashMap<String, Object>();
		viewData.put("title", "Dashboard");
		
		//Get the session user id
		HttpSession session = req.getSession();
		Session userSession = (Session) session.getAttribute("userSession");
		int userId = userSession.getUser().getId();
		
		//Get Notifications
		NotificationManager notificationMan = new NotificationManager();
		List<Notification> notifications = notificationMan.getAllNotifications(userId);		
		viewData.put("notifications", notifications);
		
		//Get Meetings
		MeetingManager meetingMan = new MeetingManager();
		List<Meeting> meetings = meetingMan.getAllMeetings(userId);
		viewData.put("meetings", meetings);
		
		//Get Groups
		GroupManager groupMan = new GroupManager();
		List<Group> groups = groupMan.getAllGroups(userId);
		viewData.put("groups", groups);
		
		//Get Documents
		DocumentManager docMan = new DocumentManager();
		List<Document> userDocuments = new LinkedList<Document>();
		for(Group g: groups){
			List<Document> documentCollection = docMan.getGroupDocuments(g.getId());
			userDocuments.addAll(documentCollection);
		}
	    viewData.put("documents", userDocuments);
		
		if (req.getMethod() == HttpMethod.Get) {
			view(req, res, "/views/home/Dashboard.jsp", viewData);
		} else if (req.getMethod() == HttpMethod.Post) {
			httpNotFound(req, res);
		}
	}
	
	/**
	 * Displays the Notifications page for a HTTP Get
	 * 
	 * - Requires a cookie for the session user
	 * - Requires a dismiss request parameter to dismiss a notification
	 * - Requires an activate request parameter to activate a user
	 * - Requires a groupId and addUserId to add a user to a group
	 * 
	 * @param req The HTTP Request
	 * @param res The HTTP Response
	 */
	public void notificationsAction(HttpServletRequest req, HttpServletResponse res) {
		//Ensure there is a cookie for the session user
		if (AccountController.redirectIfNoCookie(req, res)) return;
		
		Map<String, Object> viewData = new HashMap<String, Object>();
		viewData.put("title", "Notifications");

		//Get the session user id
		Session userSession = (Session) req.getSession().getAttribute("userSession");
		int userId = userSession.getUser().getId();

		//Get the notifications
		NotificationManager notificationMan = new NotificationManager();
		List<Notification> notifications = notificationMan.getAllNotifications(userId);
		viewData.put("notifications", notifications);
		
		if (req.getMethod() == HttpMethod.Get) {
			
			//Handle dismissed notification
			String dismissString = req.getParameter("dismiss");
			if (dismissString != null) {
				int dismissIndex = Integer.parseInt(req.getParameter("dismiss"));
				Notification deleted = notifications.remove(dismissIndex);
				notificationMan.deleteNotification(deleted.getId());
				
				//Notify of deletion
				req.setAttribute("dismissed", true);
			}
			
			//Handle activation of user
			String activationString = req.getParameter("activate");
			if (activationString != null) {
				int activationIndex = Integer.parseInt(activationString);
				
				//Activate the user
				UserManager userManager = new UserManager();
				userManager.activateUser(activationIndex);
				
				//Delete the notification
				for (Notification n : notifications) {
					if (n.getLink().equals("/home/notifications?activate=" + activationString))
						notificationMan.deleteNotification(n.getId());
				}
				
				//Notify of activation
				req.setAttribute("activated", true);
			}
			
			//Handle addition of users to groups
			String groupIdString = req.getParameter("groupId");
			String addUserIdString = req.getParameter("addUserId");
			if (groupIdString != null && addUserIdString != null) {
				int groupId = Integer.parseInt(groupIdString);
				int addUserId = Integer.parseInt(addUserIdString);
				
				//Activate the user
				GroupManager groupMan = new GroupManager();
				groupMan.createMapping(groupId, addUserId);
				
				//Delete the notification
				for (Notification n : notifications) {
					if (n.getLink().equals("/home/notifications?addUserId=" + addUserId + "&groupId=" + groupId))
						notificationMan.deleteNotification(n.getId());
				}
				
				//Notify of activation
				req.setAttribute("added", true);
			}
			
			view(req, res, "/views/home/Notification.jsp", viewData);
		} else if (req.getMethod() == HttpMethod.Post) {
			httpNotFound(req, res);
		}
	}
}
