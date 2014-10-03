/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rgms.controller;

import rgms.infrastructure.*;

/**
 *
 * @author Tyler 2
 */
public abstract class LoginController {
    
    public static Session AutoLogin() {
        Session oldSession = AuthenticationManager.LoadCurrentSession();
        return oldSession;
    }
    
    public static String Login(String username, String password) {
        Session userSession = AuthenticationManager.Login(username, password, false);
        
        //TODO: Add session to HTTP Session
        
        return (userSession != null) ?  "Dashboard.jsp" : "Login.jsp?error=true";
        
    }
    
}
