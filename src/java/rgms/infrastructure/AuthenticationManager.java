/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rgms.infrastructure;

import java.sql.ResultSet;
import java.util.logging.*;
import javax.servlet.http.HttpSession;
import rgms.model.User;

/**
 *
 * @author Tyler 2
 */
public class AuthenticationManager {
    
    public static Session LoadCurrentSession() {
        return null;
    }
    
    public static Session Login(String username, String password, boolean rememberMe) {
        Session userSession = null;
        
        try {
            JDBCConnection conn = new JDBCConnection();
            
            String query = "SELECT * FROM Users WHERE Username='" + username +
                    "' AND Password='" + password + "'" ;
            
            try {
                conn.getConnection();
                
                ResultSet rs = conn.executeQuery(query);
                if (rs != null) {
                    while (rs.next()) {
                        String rsUsername = rs.getString("Username");
                        String rsPassword = rs.getString("Password");
                        
                        if (rsUsername.equals(username) &&
                                rsPassword.equals(password)) {
                            
                            int rsUserId = Integer.parseInt(rs.getString("Id"));
                            String rsFirstName = rs.getString("Firstname");
                            String rsLastName = rs.getString("Lastname");
                            String rsImageReference = rs.getString("ImageReference");
                            
                            User thisUser = new User(rsUserId, rsFirstName, rsLastName, rsUsername, rsPassword, rsImageReference);
                            userSession = new Session(false, rsUserId, thisUser);
                        }
                            
                    }
                }
                
                conn.setDone();
            } catch (Exception e) {
                 Logger.getLogger(AuthenticationManager.class.getName()).log(Level.SEVERE, null, e);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(AuthenticationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return userSession;
    } 
}
