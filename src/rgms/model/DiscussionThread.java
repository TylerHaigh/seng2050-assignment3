package rgms.model;

public class DiscussionThread {

	//Private Instance Variables
	private int id;
	private DiscussionType discussionType;
	
	private int groupId;
	private Group group;
	
	private String threadName;

	//Constructors
	
	public DiscussionThread() {
		this.id = 1;
		this.discussionType = DiscussionType.Document;
		this.groupId = 1;
		this.group = new Group();
		this.threadName = "";
	}
	
	public DiscussionThread(int id, DiscussionType discussionType, int groupId,
			Group group, String threadName) {
		this.id = id;
		this.discussionType = discussionType;
		this.groupId = groupId;
		this.group = group;
		this.threadName = threadName;
	}

	//Getters
	
	public int getId() {
		return id;
	}

	public DiscussionType getDiscussionType() {
		return discussionType;
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

	//Setters
	
	public void setId(int id) {
		this.id = id;
	}

	public void setDiscussionType(DiscussionType discussionType) {
		this.discussionType = discussionType;
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
	
}
