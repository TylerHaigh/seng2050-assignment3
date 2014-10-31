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

public abstract class Controller extends HttpServlet {
  private static final Logger logger = Logger.getLogger(Controller.class.getName());
  public static final String LAYOUT_PATH = "/views/shared/Layout.jsp";
  private static final String BODY_ATTRIBUTE = "body";
  private static final String SCRIPTS_ATTRIBUTE = "scripts";
  private static final String STYLES_ATTRIBUTE = "styles";
  private static final String TITLE_ATTRIBUTE = "title";

  private static String getAction(URI requestUri) {
    String[] paths = requestUri.getPath().toLowerCase().split("/");
    String actionName = paths[paths.length - 1];
    logger.info("Returning action: " + actionName);

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

  protected void view(HttpServletRequest req, HttpServletResponse res, String viewPath, Map<String, String> viewData) {
    for (Map.Entry<String, String> entry : viewData.entrySet()) {
      req.setAttribute(entry.getKey(), entry.getValue());
    }

    view(req, res, viewPath);
  }

  protected void view(HttpServletRequest req, HttpServletResponse res, String viewPath) {
    req.setAttribute("partialViewMain", viewPath);

    //render layout
    renderLayout(req, res);
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

  protected void redirectToLocal(HttpServletRequest req, HttpServletResponse res, String path) {
    try {
      res.sendRedirect(req.getContextPath() + path);
      return;
    }
    catch (java.io.IOException e) {
      logger.log(Level.SEVERE, "Redirection Error", e);
    }
  }

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