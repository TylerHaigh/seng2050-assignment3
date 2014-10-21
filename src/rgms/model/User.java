package rgms.model;

import java.io.Serializable;

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
    private String studentID;
    private String password;
    private String imageReference; //Unique string containing a reference to the image on disk

    public User() {
        this.id = 1;
        this.firstName = "";
        this.lastName = "";
        this.studentID = "";
        this.password = "";
        this.imageReference = "";
    }
    
    public User(int id, String userName, String firstName, String lastName, String studentID,
            String password, String imageReference) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentID = studentID;
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

    public String getStudentID() {
        return studentID;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getImageReference() {
        return imageReference;
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

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImageReference(String imageReference) {
        this.imageReference = imageReference;
    }
    
    //Queries
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
