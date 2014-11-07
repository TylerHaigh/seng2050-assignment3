package rgms.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.logging.*;
import java.util.List;

import rgms.datacontext.GroupManager;

public class DiscussionThread implements Serializable {
	private static final Logger logger = Logger.getLogger("rgms.model.DiscussionThread");

	//Private Instance Variables
	private int id;
	private int groupId;
	private Group group;
	private String threadName;
	private List<DiscussionPost> posts;

	//Constructors
	
	public DiscussionThread() {
		this.id = 1;
		this.groupId = 1;
		this.group = new Group();
		this.threadName = "";
	}
	
	public DiscussionThread(int id, int groupId,
			Group group, String threadName) {
		this.id = id;
		this.groupId = groupId;
		this.group = group;
		this.threadName = threadName;
	}

	//Getters
	
	public int getId() {
		return id;
	}

	public int getGroupId() {
		return groupId;
	}

	public Group getGroup() {
		return group;
	}

	public String getThreadName() {
		return threadName;
	}

	public List<DiscussionPost> getPosts() {
		return this.posts;
	}

	//Setters
	
	public void setId(int id) {
		this.id = id;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public void setPosts(List<DiscussionPost> posts) {
		this.posts = posts;
	}
	
	public static DiscussionThread fromResultSet(ResultSet rs) {
		DiscussionThread thread = null;
		GroupManager gm = new GroupManager();
		try {
			if (rs.next()) {
				int groupId = rs.getInt("GroupId");
				thread = new DiscussionThread();
				thread.setId(rs.getInt("Id"));
				thread.setGroupId(groupId);
				thread.setThreadName(rs.getString("ThreadName"));
				thread.setGroup(gm.get(groupId));
			}
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, "SQL Error", e);
		}

		return thread;
	}
}
