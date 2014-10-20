package rgms.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.logging.*;

//@WebServlet(urlPatterns = { "/account/*" })
public class AccountController extends HttpServlet {
  private static final Logger logger = Logger.getLogger(AccountController.class.getName());

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
    //get the jsp
    RequestDispatcher view = req.getRequestDispatcher("views/account/login.jsp");

    try {
      //forward the request
      view.forward(req, res);
    }
    catch (Exception e) {  
      logger.log(Level.SEVERE, "Unknown Error", e);
    }
  }
}