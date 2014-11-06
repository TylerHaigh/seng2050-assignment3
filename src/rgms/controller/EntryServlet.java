package rgms.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * Allows automatic redirection from the root index page to the dashboard
 * 
 * @author Tyler Haigh - C3182929
 * @author Simon Hartcher - C3185790
 * @author Josh Crompton - C3165877
 *
 */
@WebServlet("")
public class EntryServlet extends HttpServlet {
  
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
    res.sendRedirect(req.getContextPath() + "/home/dashboard/");
  }
  
}