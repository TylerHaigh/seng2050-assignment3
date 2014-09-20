package rgms.model;

import java.io.Serializable;

/**
 * Stores all information pertaining to a User's account and profile
 * @author Tyler Haigh - C3182929
 */
public class User implements Serializable {
    
    private String name;
    private String studentID;
    private String password;
    private String imageReference; //Unique string containing a reference to the image on disk
    
    public User() {
        
    }
    
    //Getters
    
    public String getName() {
        return name;
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
    
    public void setName(String name) {
        this.name = name;
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
}
