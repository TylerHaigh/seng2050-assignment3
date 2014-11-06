package rgms.controller;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.*;

import rgms.mvc.*;
import rgms.infrastructure.*;
import rgms.model.*;
import rgms.datacontext.*;

@WebServlet(urlPatterns = { "/group/*", "/group" })
public class GroupController extends Controller{
	private static Logger logger = Logger.getLogger(GroupController.class.getName());

	public GroupController() { }
	
	public void researchgroupAction(HttpServletRequest req, HttpServletResponse res) {
	    Map<String, Object> viewData = new HashMap<String, Object>();
	    viewData.put("title", "Research Group");
	    
	    if (req.getMethod() == HttpMethod.Get) {
			
	    	GroupManager gm = new GroupManager();
		    int groupId = Integer.parseInt(req.getParameter("groupId"));
		    Group group = gm.get(groupId);
		    
		    if (group != null) {
			    viewData.put("groupName", group.getGroupName());
			    
			    List<String> groupMembers = gm.getGroupMembers(groupId);
			    viewData.put("groupMembers", groupMembers);

			    DiscussionManager dm = new DiscussionManager();
			    viewData.put("groupDiscussions", dm.getThreads(groupId));

		    	view(req, res, "/views/group/ResearchGroup.jsp", viewData);
		    } else {
		    	httpNotFound(req, res);
		    }
		    
		} else if (req.getMethod() == HttpMethod.Post) {
			//404
			httpNotFound(req, res);
		}
	}
	
	public void meetingAction(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> viewData = new HashMap<String, Object>();
	    viewData.put("title", "Meeting");
	    
	    MeetingManager meetingMan = new MeetingManager();
	    GroupManager groupMan = new GroupManager();
	    
	    if (req.getMethod() == HttpMethod.Get) {
	    	int meetingId = Integer.parseInt(req.getParameter("meetingId"));
		    Meeting meeting = meetingMan.get(meetingId);
		    
		    if (meeting != null) {
			    
		    	List<User> meetingUsers = groupMan.getGroupUsers(meeting.getGroupId());
		    	
		    	viewData.put("meetingUsers", meetingUsers);
		    	viewData.put("meeting", meeting);
			    view(req, res, "/views/group/Meeting.jsp", viewData);
			    
		    } else {
		    	httpNotFound(req, res);
		    }
	    } else if (req.getMethod() == HttpMethod.Post) {
	    	
	    	//Get details from request
	    	String description = req.getParameter("description");
	    	int createdByUserId = Integer.parseInt(req.getParameter("createdByUserId"));
	    	Date dateCreated = new Date();
	    	
	    	String meetingDate = req.getParameter("datepicker");
	    	String meetingTime = req.getParameter("meetingTime");
	    	
	    	//Parse meeting date time details
	    	DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
	    	Date dateDue = new Date();
	    	try {
				dateDue = format.parse(meetingDate + " " + meetingTime);
			} catch (ParseException e) {
				//Unable to parse date
			}
	    	
	    	int groupId = Integer.parseInt(req.getParameter("groupId"));
	    	
	    	//Create a Meeting
	    	Meeting meeting = new Meeting();
	    	meeting.setDescription(description);
	    	meeting.setCreatedByUserId(createdByUserId);
	    	meeting.setDateCreated(dateCreated);
	    	meeting.setDateDue(dateDue);
	    	meeting.setGroupId(groupId);
	    	
	    	meetingMan.createMeeting(meeting);
	    	int meetingId = meetingMan.getIdFor(meeting);
	    	
	    	//Create a notification for all users in group
	    	NotificationManager notificationMan = new NotificationManager();
	    	List<User> users = groupMan.getGroupUsers(groupId);
	    	
	    	for (User u : users) {
	    		Notification notification = new Notification(u.getId(), u, groupId,
	    				null, "Meeting " + description + " was created by " + u.getFullName(),
	    				"/group/meeting?meetingId=" + meetingId);
	    		notificationMan.createNotification(notification);
	    	}
	    	
	    	//Update the User Session to show new meeting
	    	HttpSession session = req.getSession();
	    	Session userSession = (Session) session.getAttribute("userSession");
	    	User admin = userSession.getUser();
	    	admin.getMeetings().add(meeting);
	    	
	    	//Show meeting page
	    	viewData.put("meetingUsers", users);
	    	viewData.put("meeting", meeting);
		    view(req, res, "/views/group/Meeting.jsp", viewData);
	    	
	    }
	}
	
	public void deletemeetingAction(HttpServletRequest req, HttpServletResponse res) {
		if (req.getMethod() == HttpMethod.Get) {
			
			int meetingId = Integer.parseInt(req.getParameter("meetingId"));
			MeetingManager meetingMan = new MeetingManager();
			Meeting meeting = meetingMan.get(meetingId);
			meetingMan.deleteMeeting(meetingId);
			
			//Update the User Session to remove meeting
	    	HttpSession session = req.getSession();
	    	Session userSession = (Session) session.getAttribute("userSession");
	    	List<Meeting> adminMeetings = userSession.getUser().getMeetings();

	    	for (int i = 0; i < adminMeetings.size(); i++) {
	    		Meeting m = adminMeetings.get(i);
	    		if (m.getId() == meeting.getId()) {
	    			adminMeetings.remove(i);
	    			break;
	    		}
	    	}
	    	
	    	redirectToLocal(req, res, "/home/dashboard");
			
		} else if (req.getMethod() == HttpMethod.Post) {
			httpNotFound(req, res);
		}
		
	}

	public void discussionAction(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> viewData = new HashMap<>();
		viewData.put("title", "Discussion");

		if (req.getMethod() == HttpMethod.Get) {
			int threadId = Integer.parseInt(req.getParameter("threadId"));

			DiscussionManager discussionManager = new DiscussionManager();
			DiscussionThread thread = discussionManager.getThread(threadId);
			thread.setPosts(discussionManager.getPosts(threadId));

			viewData.put("thread", thread);

			view(req, res, "/views/group/DiscussionThread.jsp", viewData);
		}
	}

	public void documentAction(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> viewData = new HashMap<>();
		viewData.put("title", "Document");

		if (req.getMethod() == HttpMethod.Get) {
			int documentId = Integer.parseInt(req.getParameter("documentId"));
			view(req, res, "/views/group/Document.jsp", viewData);
		}
		else {
			httpNotFound(req, res);
		}
	}

	public void uploadDocumentAction(HttpServletRequest req, HttpServletResponse res) {

	}
}
