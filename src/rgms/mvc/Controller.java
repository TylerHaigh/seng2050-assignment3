package rgms.mvc;

import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.util.logging.*;
import java.util.regex.*;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.*;
import java.nio.charset.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Generic Servlet that will act as a Controller as part of an MVC architecture
 * 
 * @author Tyler Haigh - C3182929
 * @author Simon Hartcher - C3185790
 * @author Josh Crompton - C3165877
 *
 */
public abstract class Controller extends HttpServlet {
  
  //Constants
  private static final Logger logger = Logger.getLogger(Controller.class.getName());
  public static final String LAYOUT_PATH = "/views/shared/Layout.jsp";
  private static final String BODY_ATTRIBUTE = "body";
  private static final String SCRIPTS_ATTRIBUTE = "scripts";
  private static final String STYLES_ATTRIBUTE = "styles";
  private static final String TITLE_ATTRIBUTE = "title";

  /**
   * Gets the action method name from the URI
   * @param requestUri The uri requested
   * @return The action name of the controller action to run
   */
  private static String getAction(URI requestUri) {
    String[] paths = requestUri.getPath().toLowerCase().split("/");
    String actionName = paths[paths.length - 1];
    logger.info("Returning action: " + actionName);

    return actionName;
  }

  /**
   * Gets the action method name from a request string
   * @param requestString The request path
   * @return The action name of the controller action to run
   */
  private static String getAction(String requestString) {
    try {
      return getAction(new URI(requestString));
    }
    catch (URISyntaxException e) {
      logger.log(Level.SEVERE, "URI Error", e);
      return null;
    }
  }

  /**
   * Performs a HTTP GET method
   */
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    routeRequest(req, resp);
  }

  /**
   * Performs a HTTP POST method
   */
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    routeRequest(req, resp);
  }

  /**
   * Gets the action method to be run based on the name of the action 
   * @param actionName The name of the action to run
   * @return The method to run
   */
  private Method getActionMethod(String actionName) {
    Method[] methods = this.getClass().getDeclaredMethods();
    for (Method m: methods) {
      if (m.getName().equalsIgnoreCase(actionName + "Action")) {
        return m;
      }
    }

    return null;
  }

  /**
   * Renders a view with supplied view data
   * @param req A HTTP Request
   * @param res A HTTP Response
   * @param viewPath The path of the view
   * @param viewData The data to be displayed on the view
   */
  protected void view(HttpServletRequest req, HttpServletResponse res, String viewPath, Map<String, Object> viewData) {
    
	//Create request parameters for each entry in the view data
	for (Map.Entry<String, Object> entry : viewData.entrySet()) {
      req.setAttribute(entry.getKey(), entry.getValue());
    }

    view(req, res, viewPath);
  }

  /**
   * Renders the layout for a view
   * @param req A HTTP Request
   * @param res A HTTP Response
   * @param viewPath The path of the view
   */
  protected void view(HttpServletRequest req, HttpServletResponse res, String viewPath) {
    req.setAttribute("partialViewMain", viewPath);

    //render layout
    renderLayout(req, res);
  }

  /**
   * Determines the route to be invoked by the controller
   * @param req A HTTP Request
   * @param res A HTTP Response
   */
  private void routeRequest(HttpServletRequest req, HttpServletResponse res) {
    try {
      //get action name
      String actionName = Controller.getAction(req.getRequestURL().toString());

      //find action method on class
      Method actionMethod = this.getActionMethod(actionName);

      //no action found
      if (actionMethod == null) {
        //404 then return
        httpNotFound(req, res);
        return;
      }

      actionMethod.invoke(this, req, res);
    }
    catch (IllegalAccessException | InvocationTargetException e) {
      logger.log(Level.SEVERE, "Action Method Error", e);
    }
  }

  /**
   * Displays a HTTP 404 Page Not Found error page
   * @param req A HTTP Request
   * @param res A HTTP Response
   */
  protected void httpNotFound(HttpServletRequest req, HttpServletResponse res) {
	  Map<String, Object> viewData = new HashMap<String, Object>();
	  viewData.put("title", "Http Not Found");
	  
	  view(req, res, "/views/shared/HttpNotFound.jsp", viewData);
  }

  /**
   * Redirects the response to the given view path
   * @param req A HTTP Request
   * @param res A HTTP Response
   * @param path The view path
   */
  protected static void redirectToLocal(HttpServletRequest req, HttpServletResponse res, String path) {
    try {
      res.sendRedirect(req.getContextPath() + path);
      return;
    }
    catch (java.io.IOException e) {
      logger.log(Level.SEVERE, "Redirection Error", e);
    }
  }

  /**
   * Renders the layout for the page and display the page
   * @param req A HTTP Request
   * @param res A HTTP Response
   */
  private void renderLayout(HttpServletRequest req, HttpServletResponse res) {
    try{
      RequestDispatcher rd = req.getRequestDispatcher(LAYOUT_PATH);
      rd.forward(req, res);
      return;
    }
    catch (ServletException e) {
      logger.log(Level.SEVERE, "Servlet Error", e);
    }
    catch (IOException e) {
      logger.log(Level.SEVERE, "IO Error", e);
    }
  }
}