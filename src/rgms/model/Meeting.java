package rgms.model;

import java.sql.*;
import java.util.Date;
import java.util.logging.*;
import java.io.Serializable;

public class Meeting implements Serializable {

	//Private Instance Variables
	private int id;
	private String description;
	
	private int createdByUserId;
	private User createdByUser;
	
	private Date dateCreated;
	private Date dateDue;
	
	private int groupId;
	private Group group;
	
	//Constructors
	
	public Meeting() {
		this.id = 1;
		this.description = "";
		this.createdByUserId = 1;
		this.createdByUser = new User();
		this.dateCreated = new Date();
		this.dateDue = new Date();
		this.groupId = 1;
		this.group = new Group();
	}

	public Meeting(int id, String description, int createdByUserId, User createdByUser,
			Date dateCreated, Date dateDue, int groupId, rgms.model.Group group) {
		
		this.id = id;
		this.description = description;
		this.createdByUserId = createdByUserId;
		this.createdByUser = createdByUser;
		this.dateCreated = dateCreated;
		this.dateDue = dateDue;
		this.groupId = groupId;
		this.group = group;
	}

	//Getters
	
	public int getId() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}

	public int getCreatedByUserId() {
		return createdByUserId;
	}

	public User getCreatedByUser() {
		return createdByUser;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public Date getDateDue() {
		return dateDue;
	}

	public int getGroupId() {
		return groupId;
	}

	public Group getGroup() {
		return group;
	}

	//Setters
	
	public void setId(int id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setCreatedByUserId(int createdByUserId) {
		this.createdByUserId = createdByUserId;
	}

	public void setCreatedByUser(User createdByUser) {
		this.createdByUser = createdByUser;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setDateDue(Date dateDue) {
		this.dateDue = dateDue;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	
	//Queries
	
	public static Meeting fromResultSet(ResultSet rs) {
		Meeting meeting = null;
		
		try {

			if (rs.next()) {
				meeting = new Meeting();
				meeting.setId(rs.getInt("Id"));
				meeting.setDescription(rs.getString("Description"));
				meeting.setCreatedByUserId(rs.getInt("CreatedByUserId"));
				meeting.setDateCreated(new Date(rs.getDate("DateCreated").getTime()));
				meeting.setDateDue(new Date(rs.getDate("DateDue").getTime()));
				meeting.setGroupId(rs.getInt("GroupId"));
				
				Logger.getLogger("rgms.model.Meeting").info(
					String.format("Loaded Meeting: %d, %s, %d, %s, %s, %d",
						meeting.getId(), meeting.getDescription(), meeting.getCreatedByUserId(),
						meeting.getDateCreated().toString(), meeting.getDateDue().toString(),
						meeting.getGroupId())
				);
			}
				
		} catch (SQLException e) {
			Logger.getLogger("rgms.model.Meeting").log(Level.SEVERE, "SQL Error", e);
		}
		
		return meeting;
	}
}
