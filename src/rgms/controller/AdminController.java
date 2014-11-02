package rgms.controller;

import java.util.*;

import javax.servlet.annotation.*;
import javax.servlet.http.*;

import rgms.mvc.Controller;

@WebServlet(urlPatterns = { "/admin/*", "/admin"})
public class AdminController extends Controller {

	public AdminController() { }
	
	public void adminAction(HttpServletRequest req, HttpServletResponse res) {
		Map<String, String> viewData = new HashMap<String, String>();
		viewData.put("title", "Admin");
		  
		view(req, res, "/views/admin/Admin.jsp", viewData);
	}
}
