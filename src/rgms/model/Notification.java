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
	
	private NotificationType notificationType;
	
	private int registeringUserId;
	private User registeringUser;
	
	private int meetingId;
	private Meeting meeting;
	
	private int documentId;
	private Document document;
	
	private int discussionPostId;
	private DiscussionPost discussionPost;
	
	private String description;
	
	//Constructors
	
	public Notification() {
		
		this.id = 1;
		this.userId = 1;
		this.user = new User();
		this.groupId = 1;
		this.group = new Group();
		
		this.notificationType = NotificationType.Undefined;
		this.description = "";
	}
	
	/**
	 * Registering User Notification
	 * @param id
	 * @param userId
	 * @param user
	 * @param notificationType
	 * @param registeringUserId
	 * @param registeringUser
	 * @param description
	 */
	public Notification(int userId, User user, NotificationType notificationType,
			User registeringUser, String description) {
		
		this.userId = userId;
		this.user = user;
		
		this.notificationType = notificationType;
		this.registeringUser = registeringUser;
		this.description = description;
	}
	
	/**
	 * Meeting Notification
	 * @param id
	 * @param userId
	 * @param user
	 * @param notificationType
	 * @param meetingId
	 * @param meeting
	 * @param description
	 */
	public Notification(int userId, User user, int groupId, Group group,
			NotificationType notificationType, Meeting meeting, String description) {
		
		this.userId = userId;
		this.user = user;
		
		this.groupId = groupId;
		this.group = group;
		
		this.notificationType = notificationType;
		this.meeting = meeting;
		this.description = description;
	}

	/**
	 * Document Notification
	 * @param id
	 * @param userId
	 * @param user
	 * @param notificationType
	 * @param meetingId
	 * @param meeting
	 * @param description
	 */
	public Notification(int userId, User user, int groupId, Group group,
			NotificationType notificationType, Document document,
			String description) {
		
		this.userId = userId;
		this.user = user;
		
		this.groupId = groupId;
		this.group = group;
		
		this.notificationType = notificationType;
		this.document = document;
		this.description = description;
	}
	
	/**
	 * Discussion Post Notification
	 * @param id
	 * @param userId
	 * @param user
	 * @param groupId
	 * @param group
	 * @param notificationType
	 * @param discussionPostId
	 * @param dicussionPost
	 * @param description
	 */
	public Notification(int userId, User user, int groupId, Group group,
			NotificationType notificationType, DiscussionPost discussionPost,
			String description) {
		
		this.userId = userId;
		this.user = user;
		
		this.groupId = groupId;
		this.group = group;
		
		this.notificationType = notificationType;
		this.discussionPost = discussionPost;
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

	public NotificationType getNotificationType() {
		return notificationType;
	}
	
	public int getRegisteringUserId() {
		return registeringUserId;
	}
	
	public User getRegisteringUser() {
		return registeringUser;
	}
	
	public int getMeetingId() {
		return meetingId;
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public int getDocumentId() {
		return documentId;
	}

	public Document getDocument() {
		return document;
	}
	
	public int getDiscussionPostId() {
		return discussionPostId;
	}

	public DiscussionPost getDiscussionPost() {
		return discussionPost;
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

	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}
	
	public void setRegisteringUserId(int registeringUserId) {
		this.registeringUserId = registeringUserId;
	}
	
	public void setRegisteringUser(User registeringUser) {
		this.registeringUser = registeringUser;
	}
	
	public void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
	
	public void setDiscussionPostId(int discussionPostId) {
		this.discussionPostId = discussionPostId;
	}

	public void setDiscussionPost(DiscussionPost discussionPost) {
		this.discussionPost = discussionPost;
	}

	public void setDescription(String description) {
		this.description = description;
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
				
				int notificationTypeId = rs.getInt("NotificationTypeId");
				NotificationType type = NotificationType.fromInteger(notificationTypeId);
				
				if (type == NotificationType.RegisteringUser) {
					notification.setRegisteringUserId(rs.getInt("RegisteringUserId"));
				} else if (type == NotificationType.Meeting) {
					notification.setGroupId(rs.getInt("GroupId"));
					notification.setMeetingId(rs.getInt("MeetingId"));
				} else if (type == NotificationType.Document) {
					notification.setGroupId(rs.getInt("GroupId"));
					notification.setDocumentId(rs.getInt("DocumentId"));
				} else if (type == NotificationType.DiscussionPost) {
					notification.setGroupId(rs.getInt("GroupId"));
					notification.setDiscussionPostId(rs.getInt("DiscussionPostId"));
				}
				
				Logger.getLogger("rgms.model.Notification")
                .info(String.format("Loaded User: %d, %d, %s",
                    notification.getId(), notification.getUserId(), notification.getDescription()));
				
			}
		} catch (SQLException e) {
			Logger.getLogger("rgms.model.Notification").log(Level.SEVERE, "SQL Error", e);
		}
		
		return notification;
	}
}
