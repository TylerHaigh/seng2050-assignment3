package rgms.controller;

import java.io.*;
import java.net.URISyntaxException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.logging.*;
import java.util.regex.*;

@WebServlet(urlPatterns = { "/account/*", "/account" })
public class AccountController extends HttpServlet {
  private static final Logger logger = Logger.getLogger(AccountController.class.getName());
  private static final Pattern pattern = Pattern.compile("[.]*/account/([a-zA-Z]*)[?/][.]*", Pattern.CASE_INSENSITIVE);

  public AccountController() { }

  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    routeRequest(req, resp);
  }

  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    routeRequest(req, resp);
  }

  private void login() {

  }

  private void routeRequest(HttpServletRequest req, HttpServletResponse res) {
    String actionName = Controller.getAction(req.getRequestURL().toString());
    view(res, req, actionName);
  }

  public void view(HttpServletResponse res, HttpServletRequest req, String actionName) {
    //todo: abstract this out
    String viewFile = null;
    switch (actionName) {
      case "login": 
        viewFile = "/views/account/Login.jsp";
        break;

      default: 
        break;
    }

    try {
      RequestDispatcher view = req.getRequestDispatcher(viewFile);
      view.forward(req, res);
    }
    catch (ServletException e) {
      logger.log(Level.SEVERE, "Servlet Error", e);
    }
    catch (IOException e) {
      logger.log(Level.SEVERE, "IO Error", e);
    }
  }
}