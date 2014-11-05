package rgms.model;

import java.io.Serializable;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Notification implements Serializable {

	//Private instance variables
	private int id;
	
	private int userId;
	private User user;
	
	private int groupId;
	private Group group;
	
	private String description;
	private String link;
	
	//Constructors
	
	public Notification() {
		
		this.id = 1;
		this.userId = 1;
		this.user = new User();
		this.groupId = 1;
		this.group = new Group();
		
		this.description = "";
		this.link = "";
	}
	
	public Notification(int userId, User user, String description, String link) {
		
		this.userId = userId;
		this.user = user;
		
		this.description = description;
		this.link = link;
	}
	
	public Notification(int userId, User user, int groupId, Group group,
			String description, String link) {
		
		this.userId = userId;
		this.user = user;
		
		this.groupId = groupId;
		this.group = group;
		
		this.description = description;
		this.link = link;
	}
	
	//Getters
	
	public int getId() {
		return id;
	}

	public int getUserId() {
		return userId;
	}

	public User getUser() {
		return user;
	}

	public int getGroupId() {
		return groupId;
	}

	public Group getGroup() {
		return group;
	}

	public String getDescription() {
		return this.description;
	}
	
	public String getLink() {
		return this.link;
	}

	//Setters
	
	public void setId(int id) {
		this.id = id;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	//Queries
	
	public static Notification fromResultSet(ResultSet rs) {
		Notification notification = null;
		
		try {
			if (rs.next()) {
				
				notification = new Notification();
				
				notification.setId(rs.getInt("Id"));
				notification.setUserId(rs.getInt("UserId"));
				notification.setDescription(rs.getString("Description"));
				notification.setLink(rs.getString("Link"));
				
				Logger.getLogger("rgms.model.Notification")
                .info(String.format("Loaded User: %d, %d, %s",
                    notification.getId(), notification.getUserId(),
                    notification.getDescription(), notification.getLink() ));
				
			}
		} catch (SQLException e) {
			Logger.getLogger("rgms.model.Notification").log(Level.SEVERE, "SQL Error", e);
		}
		
		return notification;
	}
}
