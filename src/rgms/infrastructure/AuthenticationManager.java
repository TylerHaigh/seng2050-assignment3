package rgms.infrastructure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.*;

import rgms.model.User;
import rgms.datacontext.UserManager;

/**
 *	Provides a means of authenticating Users in the system
 *
 * @author Tyler Haigh - C3182929
 * @author Simon Hartcher - C3185790
 * @author Josh Crompton - C3165877
 */
public class AuthenticationManager {
    
	private static final Logger logger = Logger.getLogger(AuthenticationManager.class.getName());
	
	/**
	 * Logs the User into the system
	 * @param username The User's UserName
	 * @param password The User's plain password
	 * @param rememberMe A check to create a persistent session
	 * @return A Session for the User
	 */
    public static Session login(String username, String password, boolean rememberMe) {
        Session userSession = null;
        
        try {
            UserManager userManager = new UserManager();
            User user = userManager.get(username);
            
            //Validate the user
            if (!(user == null) && userManager.validate(username, password)) {
                userSession = new Session(false, user.getId(), user);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unknown Error", e);
        }
        
        return userSession;
    }
}
