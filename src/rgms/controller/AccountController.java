package rgms.controller;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.logging.*;
import rgms.mvc.*;
import rgms.infrastructure.*;
import rgms.model.User;
import rgms.datacontext.UserManager;

@WebServlet(urlPatterns = { "/account/*", "/account" })
public class AccountController extends Controller {
  private static final Logger logger = Logger.getLogger(AccountController.class.getName());

  public AccountController() {
  }

  public void loginAction(HttpServletRequest req, HttpServletResponse res) {
    if (req.getMethod() == HttpMethod.Get) {
      String viewFile = "/views/account/Login.jsp";

      view(req, res, viewFile);
    }
    else if (req.getMethod() == HttpMethod.Post) {
      String userName = req.getParameter("userName");
      String password = req.getParameter("password");
      // boolean rememberMe = Boolean.parse(req.getParameter("rememberMe"));

      Session userSession = AuthenticationManager.login(userName, password, false);
      if (userSession == null) {
        req.setAttribute("loginError", true);
        view(req, res, "/views/account/Login.jsp");
      }
      else {
        HttpSession session = req.getSession();
        session.setAttribute("userSession", userSession);
        redirectToLocal(req, res, "/home/dashboard");
      }
    }
  }

  public void registerAction(HttpServletRequest req, HttpServletResponse res) {
    if (req.getMethod() == HttpMethod.Get) {
      view(req, res, "/views/account/Register.jsp");
    }
    else if (req.getMethod() == HttpMethod.Post) {
      User user = new User();
      user.setUserName(req.getParameter("userName"));
      user.setFirstName(req.getParameter("firstName"));
      user.setLastName(req.getParameter("lastName"));
      user.setStudentId(req.getParameter("studentId"));

      UserManager userManager = new UserManager();
      userManager.createUser(user, req.getParameter("password"));

      Session userSession = AuthenticationManager.login(user.getUserName(), user.getPassword(), false);
      if (userSession == null) {
        req.setAttribute("registerError", true);
        view(req, res, "/views/account/Register.jsp");
      }
      else {
        HttpSession session = req.getSession();
        session.setAttribute("userSession", userSession);
        redirectToLocal(req, res, "/account/profile/");
      }
    }
  }

  public void profileAction(HttpServletRequest req, HttpServletResponse res) {
    view(req, res, "/views/account/Profile.jsp");
  }
}