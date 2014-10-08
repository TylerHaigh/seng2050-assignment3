/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rgms.controller;

import javax.servlet.http.HttpSession;
import rgms.infrastructure.*;

/**
 *
 * @author Tyler 2
 */
public class LoginController {
    
    private HttpSession session;
    
    public LoginController(HttpSession session) {
        this.session = session;
    }
    
    public String autoLogin() {
        
        Session oldSession = (Session)session.getAttribute("session");
        
        if (oldSession == null) session.setAttribute("session", oldSession);
        
        if (oldSession != null && oldSession.getUser() != null)  return "Dashboard.jsp";
        else return null;
    }
    
    public String login(String username, String password) {
        Session userSession = AuthenticationManager.Login(username, password, false);
        session.setAttribute("session", userSession);
        return (userSession != null) ?  "Dashboard.jsp" : "Login.jsp?error=true";
    }
}
