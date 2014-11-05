package rgms.controller;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

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
			    
			    List<String> groupMembers = gm.getGroupMembers(req.getParameter("groupId"));
			    viewData.put("groupMembers", groupMembers);
		    	
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
	    
	    if (req.getMethod() == HttpMethod.Get) {
	    	int meetingId = Integer.parseInt(req.getParameter("meetingId"));
		    MeetingManager meetingMan = new MeetingManager();
		    Meeting meeting = meetingMan.get(meetingId);
		    
		    if (meeting != null) {
			    viewData.put("meeting", meeting);
			    view(req, res, "/views/group/Meeting.jsp", viewData);
		    } else {
		    	httpNotFound(req, res);
		    }
	    } else {
	    	httpNotFound(req, res);
	    }
	}
	
}
