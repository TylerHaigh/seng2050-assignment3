package rgms.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.logging.*;

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
    private boolean isAdmin;

    public static User fromResultSet(ResultSet rs) {
        User user = new User();

        try {
            if (rs.first()) {
                user.setId(rs.getInt("Id"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setUserName(rs.getString("Username"));
                user.setStudentId(rs.getString("StudentId"));
                user.setPassword(rs.getString("Passphrase"));
                user.setImageReference(rs.getString("ImageReference"));
                user.setAdmin(Boolean.parseBoolean(rs.getString("IsAdmin")));
                
                Logger.getLogger("rgms.model.User")
                    .info(String.format("Loaded User: %d, %s, %s, %s, %s, %s",
                        user.getId(), user.getFirstName(), user.getLastName(),
                        user.getPassword(), user.getStudentId(), 
                        user.getImageReference()));
            }
        }
        catch (java.sql.SQLException e) {
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
    }
    
    public User(int id, String userName, String firstName, String lastName, String studentId,
            String password, String imageReference) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentId = studentId;
        this.password = password;
        this.imageReference = imageReference;
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
		return isAdmin;
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
    
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
    
    //Queries
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
