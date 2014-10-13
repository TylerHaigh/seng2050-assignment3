/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rgms.controller;

import java.io.Serializable;

import javax.servlet.http.HttpSession;

import rgms.infrastructure.*;

/**
 *
 * @author Tyler 2
 */
@SuppressWarnings("serial")
public class LoginController implements Serializable {
    
    private HttpSession session;
    
    public LoginController() {
    }
    
    public HttpSession getSession() {
    	return this.session;
    }
    
    public void setSession(HttpSession session) {
    	this.session = session;
    }
    
    public String autoLogin() {
        
        Session oldSession = (Session)session.getAttribute("userSession");
        
        //if (oldSession == null) session.setAttribute("userSession", oldSession);
        
        if (oldSession != null && oldSession.getUser() != null)  return "Dashboard.jsp";
        else return null;
    }
    
    public String login(String username, String password) {
        Session userSession = AuthenticationManager.Login(username, password, false);
        
        if (userSession != null) {
        	session.setAttribute("userSession", userSession);
        	return "Dashboard.jsp";
        } else {
        	return "Login.jsp?error=true";
        }
        
    }
}
