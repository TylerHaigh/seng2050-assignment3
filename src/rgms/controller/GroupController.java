package rgms.controller;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;

import rgms.mvc.*;
import rgms.infrastructure.*;
import rgms.model.*;
import rgms.datacontext.*;

@WebServlet(urlPatterns = { "/group*", "/group" })
public class GroupController extends Controller{
	private static Logger logger = Logger.getLogger("rgms.GroupController");

	public GroupController() { }
	
	public void researchGroupAction(HttpServletRequest req, HttpServletResponse res) {
	    Map<String, Object> viewData = new HashMap<String, Object>();
	    viewData.put("title", "Research Group");

			if (req.getMethod() == HttpMethod.Get) {
				view(req, res, "/views/group/ResearchGroup.jsp", viewData);
			} else if (req.getMethod() == HttpMethod.Post) {
				//404
				httpNotFound(res);
			}
		}
	
	
	public void createAction(HttpServletRequest req, HttpServletResponse res){
		
	}
	
}
