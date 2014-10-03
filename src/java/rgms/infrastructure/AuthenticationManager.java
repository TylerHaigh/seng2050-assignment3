package rgms.infrastructure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.*;
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
                    "' AND Passphrase='" + password + "'" ;
            
            try {
                conn.getConnection();
                
                ResultSet rs = conn.executeQuery(query);
                
                /**
                 * Since usernames are unique, there should only be at most
                 * one set returned by the query
                 */
                if (rs != null) {
                    userSession = createSession(rs);
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
    
    private static Session createSession(ResultSet rs) throws SQLException {
        
        String rsUsername = rs.getString("Username");
        String rsPassword = rs.getString("Passphrase");
        int rsUserId = Integer.parseInt(rs.getString("Id"));
        String rsFirstName = rs.getString("Firstname");
        String rsLastName = rs.getString("Lastname");
        String rsImageReference = rs.getString("ImageReference");

        User thisUser = new User(rsUserId, rsFirstName, rsLastName, rsUsername, rsPassword, rsImageReference);
        Session session = new Session(false, rsUserId, thisUser);
        return session;
    }
}
