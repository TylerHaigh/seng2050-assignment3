package rgms.controller;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.logging.*;
import rgms.mvc.*;
import rgms.infrastructure.*;

@WebServlet(urlPatterns = { "/account/*", "/account" })
public class AccountController extends Controller {
  private static final Logger logger = Logger.getLogger(AccountController.class.getName());

  public AccountController() { }

  public void loginAction(HttpServletRequest req, HttpServletResponse res) {
    if (req.getMethod() == "GET") {
      String viewFile = "/views/account/Login.jsp";

      view(req, res, viewFile);
    }
    else if (req.getMethod() == "POST") {
      String userName = req.getParameter("userName");
      String password = req.getParameter("password");
      // boolean rememberMe = Boolean.parse(req.getParameter("rememberMe"));

      Session userSession = AuthenticationManager.login(userName, password, false);
      HttpSession session = req.getSession();
      session.setAttribute("userSession", userSession);
    }
  }

  public void registerAction(HttpServletRequest req, HttpServletResponse res) {
    view(req, res, "/views/account/Register.jsp");
  }
}