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
import rgms.mvc.*;

@WebServlet(urlPatterns = { "/account/*", "/account" })
public class AccountController extends Controller {
  private static final Logger logger = Logger.getLogger(AccountController.class.getName());

  public AccountController() { }

  public void loginAction(HttpServletRequest req, HttpServletResponse res) {
    String viewFile = "/views/account/Login.jsp";

    view(req, res, viewFile);
  }

}