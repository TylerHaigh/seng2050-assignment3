/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rgms.controller;

import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tyler 2
 */
public class LoginController {
    
    private JDBCConnection conn;
    
    public LoginController() {
        try {
            conn = new JDBCConnection();
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String login(String username, String password) {
        String query = "SELECT * FROM Users WHERE Username='" + username +
                "' AND Password='" + password + "'" ;
        
        boolean validated = false;
        
        try {
            conn.getConnection();
            
            ResultSet rs = conn.executeQuery(query);
            if (rs != null) {
                while (rs.next()) {
                    if (rs.getString("Username").equals(username) &&
                        rs.getString("Password").equals(password))
                        validated = true;
                }
            }
            
            conn.setDone();
        } catch (Exception e) {
            
        }
        
        return (validated) ?  "Dashboard.jsp" : "Login.jsp?error=true";
    }
    
}
