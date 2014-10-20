package rgms.infrastructure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.*;

import rgms.model.User;
import rgms.datacontext.UserManager;

/**
 *
 * @author Tyler 2
 * @author Simon Hartcher
 */
public class AuthenticationManager {
    
	private static final Logger logger = Logger.getLogger(AuthenticationManager.class.getName());
	
	public static Session LoadCurrentSession() {
        return null;
    }
    
    public static Session Login(String username, String password, boolean rememberMe) {
        Session userSession = null;
        
        try {

            UserManager userManager = new UserManager();
            User user = userManager.get(username);
            
            /**
             * Since usernames are unique, there should only be at most
             * one set returned by the query
             */
            if (!(user == null) && userManager.validate(username, password)) {
                userSession = new Session(false, user.getId(), user);
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unknown Error", e);
        }
        
        return userSession;
    }
}
