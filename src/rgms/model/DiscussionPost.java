package rgms.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.logging.*;
import rgms.datacontext.UserManager;

public class DiscussionPost implements Serializable {
	private static final Logger logger = Logger.getLogger("rgms.model.DiscussionPost");

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
	
	public static DiscussionPost fromResultSet(ResultSet rs) {
		DiscussionPost post = null;

		try {
			if (rs.next()) {
				post = new DiscussionPost();
				post.setId(rs.getInt("Id"));
				post.setThreadId(rs.getInt("ThreadId"));
				post.setUserId(rs.getInt("UserId"));
				post.setMessage(rs.getString("Message"));

				//get user
				UserManager userManager = new UserManager();
				post.setUser(userManager.get(post.getUserId()));
			}
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, "SQL Error", e);
		}

		return post;
	}
}
