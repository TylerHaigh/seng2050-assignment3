package rgms.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("")
public class EntryServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
    res.sendRedirect(req.getContextPath() + "/home/dashboard/");
  }
}