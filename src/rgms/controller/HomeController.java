package rgms.controller;

import java.util.logging.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import rgms.mvc.Controller;
import rgms.mvc.HttpMethod;

@WebServlet(urlPatterns = { "/home/*", "/home" })
public class HomeController extends Controller {

	private static final Logger logger = Logger.getLogger(HomeController.class.getName());
	
	public HomeController() {
		ConsoleHandler consoleHandler = new ConsoleHandler();
		Logger.getLogger("").addHandler(consoleHandler);
	}
	
	public void dashboardAction(HttpServletRequest req, HttpServletResponse res) {
		if (req.getMethod() == HttpMethod.Get) {
			view(req, res, "/views/home/Dashboard.jsp");
		} else if (req.getMethod() == HttpMethod.Post) {
			//404
			httpNotFound(res);
		}
	}
	
}
