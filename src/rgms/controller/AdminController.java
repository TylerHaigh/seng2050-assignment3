package rgms.controller;

import java.util.*;

import javax.servlet.annotation.*;
import javax.servlet.http.*;

import rgms.mvc.Controller;

@WebServlet(urlPatterns = { "/admin/*", "/admin"})
public class AdminController extends Controller {

	public AdminController() { }
	
	public void adminAction(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> viewData = new HashMap<String, Object>();
		viewData.put("title", "Admin");
		  
		view(req, res, "/views/admin/Admin.jsp", viewData);
	}
}
