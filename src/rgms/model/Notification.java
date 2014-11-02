package rgms.model;

import java.io.Serializable;

public class Notification implements Serializable {

	//Private instance variables
	private int id;
	
	private int userId;
	private User user;
	
	private int groupId;
	private Group group;
	
	private int meetingId;
	private Meeting meeting;
	
	private int discussionPostId;
	private DiscussionPost discssionPost;
	
	private String description;
	
	//Constructors
	
	public Notification() {
		
		this.id = 1;
		this.userId = 1;
		this.user = new User();
		this.groupId = 1;
		this.group = new Group();
		this.meetingId = 1;
		this.meeting = new Meeting();
		this.discussionPostId = 1;
		this.discssionPost = new DiscussionPost();
		this.description = "";
	}
	
	public Notification(int id, int userId, User user, int groupId,
			Group group, int meetingId, Meeting meeting, int discussionPostId,
			DiscussionPost discssionPost, String description) {
		
		this.id = id;
		this.userId = userId;
		this.user = user;
		this.groupId = groupId;
		this.group = group;
		this.meetingId = meetingId;
		this.meeting = meeting;
		this.discussionPostId = discussionPostId;
		this.discssionPost = discssionPost;
		this.description = description;
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

	public int getMeetingId() {
		return meetingId;
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public int getDiscussionPostId() {
		return discussionPostId;
	}

	public DiscussionPost getDiscssionPost() {
		return discssionPost;
	}
	
	public String getDescription() {
		return this.description;
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

	public void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	public void setDiscussionPostId(int discussionPostId) {
		this.discussionPostId = discussionPostId;
	}

	public void setDiscssionPost(DiscussionPost discssionPost) {
		this.discssionPost = discssionPost;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
