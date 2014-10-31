package rgms.controller;

import java.util.logging.*;
import java.util.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import rgms.mvc.Controller;
import rgms.mvc.HttpMethod;

@WebServlet(urlPatterns = { "/home/*", "/home" })
public class HomeController extends Controller {

	private static final Logger logger = Logger.getLogger(HomeController.class.getName());
	
	public HomeController() { }
	
	public void dashboardAction(HttpServletRequest req, HttpServletResponse res) {
    Map<String, String> viewData = new HashMap<String, String>();
    viewData.put("title", "Dashboard");

		if (req.getMethod() == HttpMethod.Get) {
			view(req, res, "/views/home/Dashboard.jsp", viewData);
		} else if (req.getMethod() == HttpMethod.Post) {
			//404
			httpNotFound(res);
		}
	}	
}
