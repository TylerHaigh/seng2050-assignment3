package rgms.controller;

import java.util.logging.*;
import java.util.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import rgms.datacontext.*;
import rgms.infrastructure.*;
import rgms.model.*;
import rgms.mvc.*;

@WebServlet(urlPatterns = { "/home/*", "/home" })
public class HomeController extends Controller {

	private static final Logger logger = Logger.getLogger(HomeController.class.getName());
	
	public HomeController() { }
	
	public void dashboardAction(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> viewData = new HashMap<String, Object>();
		viewData.put("title", "Dashboard");
		
		Session userSession = (Session) req.getSession().getAttribute("userSession");
		int userId = userSession.getUser().getId();
		
		NotificationManager notificationMan = new NotificationManager();
		List<Notification> notifications = notificationMan.getAllNotifications(userId);		
		viewData.put("notifications", notifications);
		//Load Document into Map
		GroupManager groupMan = new GroupManager();
		UserManager userMan = new UserManager();
		User currentUser = userMan.get(userId);
		List<Document> userDocuments = new LinkedList<Document>();
		for(Group g: currentUser.getGroups()){
			userDocuments = groupMan.getGroupDocuments(Integer.toString(g.getId()));
		}
	    viewData.put("userDocuments", userDocuments);
		
		if (req.getMethod() == HttpMethod.Get) {
			view(req, res, "/views/home/Dashboard.jsp", viewData);
		} else if (req.getMethod() == HttpMethod.Post) {
			//404
			httpNotFound(req, res);
		}
	}
	
	public void notificationsAction(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> viewData = new HashMap<String, Object>();
		viewData.put("title", "Notifications");

		Session userSession = (Session) req.getSession().getAttribute("userSession");
		int userId = userSession.getUser().getId();
		
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
					if (n.getLink().equals("/home/notifications?addUserId=" + userId + "&groupId=" + groupId))
						notificationMan.deleteNotification(n.getId());
				}
				
				//Notify of activation
				req.setAttribute("added", true);
			}
			
			view(req, res, "/views/home/Notification.jsp", viewData);
		} else if (req.getMethod() == HttpMethod.Post) {
			//404
			httpNotFound(req, res);
		}
	}
}
