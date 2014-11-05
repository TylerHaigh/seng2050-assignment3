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
	    GroupManager gm = new GroupManager();
	    viewData.put("groupName", gm.get(Integer.parseInt(req.getParameter("groupId"))).getGroupName());
	    
	    List<String> groupMembers = new LinkedList<String>();
	    groupMembers = gm.getGroupMembers(req.getParameter("groupId"));
	    
	    viewData.put("groupMembers", groupMembers);
			if (req.getMethod() == HttpMethod.Get) {
				view(req, res, "/views/group/ResearchGroup.jsp", viewData);
				return;
			} else if (req.getMethod() == HttpMethod.Post) {
				//404
				httpNotFound(res);
			}
		}	
	
}
