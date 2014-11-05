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
		
		if (req.getMethod() == HttpMethod.Get) {
			view(req, res, "/views/home/Dashboard.jsp", viewData);
		} else if (req.getMethod() == HttpMethod.Post) {
			//404
			httpNotFound(res);
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
			}
			
			//Handle activation of user
			String activationString = req.getParameter("activate");
			if (activationString != null) {
				int activationIndex = Integer.parseInt(activationString);
				Notification deleted = notifications.remove(activationIndex);
				notificationMan.deleteNotification(deleted.getId());
				int activatedUserId = deleted.getRegisteringUserId();
				
				UserManager userManager = new UserManager();
				userManager.activateUser(activatedUserId);
			}
			
			view(req, res, "/views/home/Notification.jsp", viewData);
		} else if (req.getMethod() == HttpMethod.Post) {
			//404
			httpNotFound(res);
		}
	}
}
