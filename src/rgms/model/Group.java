package rgms.model;

public class Group {

	//Private Instance Variables
	private int id;
	private String groupName;
	
	//Constructors
	
	public Group() {
		this.id = 0;
		this.groupName = "";
	}
	
	public Group(int id, String groupName) {
		this.id = id;
		this.groupName = groupName;
	}

	//Getters
	
	public int getId() {
		return id;
	}

	public String getGroupName() {
		return groupName;
	}

	//Setters
	
	public void setId(int id) {
		this.id = id;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	
	
}
