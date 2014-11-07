package rgms.infrastructure;

import java.util.Date;
import rgms.model.User;

/**
 * Keeps track of a User's session within RGMS. Does not reflect a HttpSession
 *
 * @author Tyler Haigh - C3182929
 * @author Simon Hartcher - C3185790
 * @author Josh Crompton - C3165877
 * 
 */
public class Session {
    
	//Private instance variables
	private static int _id = 0;
    private int id;
    private Date date;
    
    private boolean persistent;
    private int userId;
    private User user;
    
    //Constructors
    
    /**
     * Default Constructor for a Session. Initialises an empty Session
     */
    public Session() {
        this.id = _id++;
        this.date = new Date();
        this.persistent = false;
        this.userId = -1;
        this.user = null;
    }
    
    /**
     * Constructor for a Session, Sets the instance data
     * @param isPersistent The Session will persist after closing the Browser
     * @param userId The Id of the User
     * @param user The User who created the Session 
     */
    public Session(boolean isPersistent, int userId, User user) {
        this.id = _id++;
        this.date = new Date();
        this.persistent = isPersistent;
        this.userId = userId;
        this.user = user;
    }
    
    //Getters
    
    /**
     * Getter for the Session Id
     * @return The Session Id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the Session creation date
     * @return The Date the Session was created
     */
    public Date getDate() {
        return date;
    }
    
    /**
     * Checks whether the Session is persistent
     * @return True if the Session is persistent, false otherwise
     */
    public boolean isPersistent() {
        return persistent;
    }
    
    /**
     * Getter for the User Id of the Session User
     * @return The User Id of the Session User
     */
    public int getUserId() {
        return userId;
    }
    
    /**
     * Getter for the Session User
     * @return The Session User
     */
    public User getUser() {
        return user;
    }
    
    //Setters
    
    /**
     * Setter for the Session Id
     * @param id The new Session Id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter for the Session creation Date
     * @param date The new Session creation Date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Setter for whether the Session is persistent or not
     * @param persistent True if the Session is persistent, false otherwise
     */
    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    /**
     * Setter for the Session User Id
     * @param userId The new Id for the Session User
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Setter for the Session User
     * @param user The new Session User
     */
    public void setUser(User user) {
        this.user = user;
    }
    
}
