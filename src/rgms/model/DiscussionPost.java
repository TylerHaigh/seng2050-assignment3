package rgms.model;

import java.io.Serializable;

public class DiscussionPost implements Serializable {

	//Private Instance Variables
	private int id;
	
	private int threadId;
	private DiscussionThread discussionThread;
	
	private int userId;
	private User user;
	
	private String message;
	
	//Constructors
	
	public DiscussionPost() {
		this.id = 1;
		this.threadId = 1;
		this.discussionThread = new DiscussionThread();
		this.userId = 1;
		this.user = new User();
		this.message = "";
	}

	public DiscussionPost(int id, int threadId,
			DiscussionThread discussionThread, int userId, User user,
			String message) {

		this.id = id;
		this.threadId = threadId;
		this.discussionThread = discussionThread;
		this.userId = userId;
		this.user = user;
		this.message = message;
	}

	//Getters
	
	public int getId() {
		return id;
	}

	public int getThreadId() {
		return threadId;
	}

	public DiscussionThread getDiscussionThread() {
		return discussionThread;
	}

	public int getUserId() {
		return userId;
	}

	public User getUser() {
		return user;
	}

	public String getMessage() {
		return message;
	}

	//Setters
	
	public void setId(int id) {
		this.id = id;
	}

	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}

	public void setDiscussionThread(DiscussionThread discussionThread) {
		this.discussionThread = discussionThread;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}