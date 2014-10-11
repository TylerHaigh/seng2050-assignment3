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
    
	private static final Logger logger = Logger.getLogger(AuthenticationManager.class.getName());
	
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
                if (rs != null && rs.next()) {
                    userSession = CreateSession(rs);
                }
                
                conn.setDone();
            } catch (SQLException sql) {
                 logger.log(Level.SEVERE, "Unable to execute query: " + query, sql);
            }
            
        } catch (Exception jdbc) {
            logger.log(Level.SEVERE, "Unable to establish a connection to the JDBC", jdbc);
        }
        
        return userSession;
    }
    
    public static User CreateUser(ResultSet rs) throws SQLException {
    	String rsUsername = rs.getString("Username");
        String rsPassword = rs.getString("Passphrase");
        int rsUserId = Integer.parseInt(rs.getString("Id"));
        String rsFirstName = rs.getString("Firstname");
        String rsLastName = rs.getString("Lastname");
        String rsImageReference = rs.getString("ImageReference");

        User thisUser = new User(rsUserId, rsFirstName, rsLastName, rsUsername, rsPassword, rsImageReference);
        return thisUser;
    }
    
    public static Session CreateSession(ResultSet rs) throws SQLException {
        User thisUser = CreateUser(rs);
    	Session session = new Session(false, thisUser.getId(), thisUser);
        return session;
    }
}
