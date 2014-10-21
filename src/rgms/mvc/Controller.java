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

public abstract class Controller extends HttpServlet {
  private static final Logger logger = Logger.getLogger(Controller.class.getName());

  private static String getAction(URI requestUri) {
    String[] paths = requestUri.getPath().toLowerCase().split("/");
    String actionName = paths[paths.length - 1];
    System.out.println(actionName);

    return actionName;
  }

  private static String getAction(String requestString) {
    try {
      return getAction(new URI(requestString));
    }
    catch (URISyntaxException e) {
      logger.log(Level.SEVERE, "URI Error", e);
      return null;
    }
  }

  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    routeRequest(req, resp);
  }

  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    routeRequest(req, resp);
  }

  private Method getActionMethod(String actionName) {
    Method[] methods = this.getClass().getDeclaredMethods();
    for (Method m: methods) {
      if (m.getName().equals(actionName + "Action")) {
        return m;
      }
    }

    return null;
  }

  protected void view(HttpServletRequest req, HttpServletResponse res, String viewPath) {
    try {
      RequestDispatcher view = req.getRequestDispatcher(viewPath);
      view.forward(req, res); 
    }
    catch (ServletException e) {
      logger.log(Level.SEVERE, "Servlet Error", e);
    }
    catch (IOException e) {
      logger.log(Level.SEVERE, "IO Error", e);
    }
  }

  private void routeRequest(HttpServletRequest req, HttpServletResponse res) {
    try {
      //get action name
      String actionName = Controller.getAction(req.getRequestURL().toString());

      //find action method on class
      Method actionMethod = this.getActionMethod(actionName);

      //no action found
      if (actionMethod == null) {
        //404 then return
        httpNotFound(res);
        return;
      }

      actionMethod.invoke(this, req, res);
    }
    catch (IllegalAccessException | InvocationTargetException e) {
      logger.log(Level.SEVERE, "Action Method Error", e);
    }
  }

  protected void httpNotFound(HttpServletResponse res) {
    try {
      res.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
    catch (IOException e) {
      logger.log(Level.SEVERE, "IO Error", e);
    }
  }
}