package rgms.controller;

import java.io.*;
import java.net.URISyntaxException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.logging.*;
import java.util.regex.*;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

@WebServlet(urlPatterns = { "/account/*", "/account" })
public class AccountController extends HttpServlet {
  private static final Logger logger = Logger.getLogger(AccountController.class.getName());

  public AccountController() { }

  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    routeRequest(req, resp);
  }

  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    routeRequest(req, resp);
  }

  private void loginAction(HttpServletRequest req, HttpServletResponse res) {
    String viewFile = "/views/account/Login.jsp";

    view(req, res, viewFile);
  }

  private Method getActionMethod(String actionName) {
    Method[] methods = AccountController.class.getDeclaredMethods();
    for (Method m: methods) {
      if (m.getName().equals(actionName + "Action")) {
        return m;
      }
    }

    return null;
  }

  public void view(HttpServletRequest req, HttpServletResponse res, String viewPath) {
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

  public void routeRequest(HttpServletRequest req, HttpServletResponse res) {
    try {
      //get action name
      String actionName = Controller.getAction(req.getRequestURL().toString());

      //find action method on class
      Method actionMethod = this.getActionMethod(actionName);

      //no action found
      if (actionMethod == null) {
        //404 then return
        res.sendError(HttpServletResponse.SC_NOT_FOUND);
        return;
      }

      actionMethod.invoke(this, req, res);
    }
    catch (IOException e) {
      logger.log(Level.SEVERE, "IO Error", e);
    }
    catch (IllegalAccessException | InvocationTargetException e) {
      logger.log(Level.SEVERE, "Action Method Error", e);
    }
  }
}