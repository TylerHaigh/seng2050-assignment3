/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rgms.infrastructure;

import java.util.Date;
import rgms.model.User;

/**
 *
 * @author Tyler 2
 */
public class Session {
    private static int _id = 0;
    private int id;
    private Date date;
    
    private boolean persistent;
    private int userId;
    private User user;
    
    //Constructors
    
    public Session() {
        this.id = _id++;
        this.date = new Date();
        this.persistent = false;
        this.userId = -1;
        this.user = null;
    }
    
    public Session(boolean isPersistent, int userId, User user) {
        this.id = _id++;
        this.date = new Date();
        this.persistent = isPersistent;
        this.userId = userId;
        this.user = user;
    }
    
    //Getters
    
    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }
    
    public boolean isPersistent() {
        return persistent;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public User getUser() {
        return user;
    }
    
    //Setters
    
    public void setId(int id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
