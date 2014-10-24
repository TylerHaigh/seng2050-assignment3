package rgms.model;

import java.util.Date;

public class Meeting {

	//Private Instance Variables
	private int id;
	
	private int createdByUserId;
	private User createdByUser;
	
	private Date dateCreated;
	private Date dateDue;
	
	private int groupId;
	private Group group;
	
	//Constructors
	
	public Meeting() {
		this.id = 1;
		this.createdByUserId = 1;
		this.createdByUser = new User();
		this.dateCreated = new Date();
		this.dateDue = new Date();
		this.groupId = 1;
		this.group = new Group();
	}

	public Meeting(int id, int createdByUserId, User createdByUser,
			Date dateCreated, Date dateDue, int groupId, rgms.model.Group group) {
		
		this.id = id;
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
	
}
