package rgms.model;

import java.io.Serializable;
import java.sql.*;
import java.util.List;
import java.util.logging.*;

import rgms.datacontext.*;

/**
 * Stores all information pertaining to a User's account and profile
 * @author Tyler Haigh - C3182929
 * @author Simon Hartcher - C3185790
 */
public class User implements Serializable {
    
    private int id;
    private String userName;
    private String firstName;
    private String lastName;
    private String studentId;
    private String password;
    private String imageReference; //Unique string containing a reference to the image on disk
    private boolean admin;
    private boolean active;
    private String description;
    
    private List<Group> groups;
    private List<Meeting> meetings;

    public static User fromResultSet(ResultSet rs) {
    	User user = null;

        try {
            if (rs.next()) {
            	
            	//Set up data context managers
            	GroupManager groupMan = new GroupManager();
            	MeetingManager meetingMan = new MeetingManager();
            	
            	user = new User();
            	
            	//Apply the Database data to the user
            	user.setId(rs.getInt("Id"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setUserName(rs.getString("Username"));
                user.setStudentId(rs.getString("StudentId"));
                user.setPassword(rs.getString("Passphrase"));
                user.setImageReference(rs.getString("ImageReference"));
                user.setAdmin(rs.getBoolean("IsAdmin"));
                user.setActive(rs.getBoolean("IsActive"));
                user.setDescription(rs.getString("Description"));
                
                //Apply Data context specific data to user
                user.setGroups(groupMan.getAllGroups(user.getId()));
                user.setMeetings(meetingMan.getAllMeetings(user.getId()));
                
                Logger.getLogger("rgms.model.User")
                    .info(String.format("Loaded User: %d, %s, %s, %s, %s, %s",
                        user.getId(), user.getFirstName(), user.getLastName(),
                        user.getPassword(), user.getStudentId(), 
                        user.getImageReference()));
            }
        }
        catch (SQLException e) {
            Logger.getLogger("rgms.model.User").log(Level.SEVERE, "SQL Error", e);
        }

        return user;
    }

    public User() {
        this.id = 1;
        this.firstName = "";
        this.lastName = "";
        this.studentId = "";
        this.password = "";
        this.imageReference = "";
        this.admin = false;
        this.active = false;
    }
    
    public User(int id, String userName, String firstName, String lastName, String studentId,
            String password, String imageReference, boolean admin, boolean active) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentId = studentId;
        this.password = password;
        this.imageReference = imageReference;
        this.admin = admin;
        this.active = active;
    }
    
    //Getters
    
    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }

    public String getStudentId() {
        return studentId;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getImageReference() {
        return imageReference;
    }
    
    public boolean isAdmin() {
		return admin;
	}
    
    public boolean isActive() {
    	return active;
    }
    
    public String getDescription() {
    	return this.description;
    }
    
    public List<Group> getGroups() {
    	return groups;
    }
    
    public List<Meeting> getMeetings() {
    	return meetings;
    }
    
    //Setters
    
    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImageReference(String imageReference) {
        this.imageReference = imageReference;
    }
    
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
    
	public void setMeetings(List<Meeting> meetings) {
		this.meetings = meetings;
	}
	
    //Queries
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
