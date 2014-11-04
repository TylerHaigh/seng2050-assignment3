package rgms.model;

public class Coordinator extends User {

	//Private Instance Variables
	private int id;
	
	private int userId;
	private User user;
	
	//Constructors
	
	public Coordinator() {
		this.id = 1;
		this.userId = 1;
		this.user = new User();
	}
	
	public Coordinator(int id, int userId) {
		this.id = id;
		this.userId = userId;
		this.user = new User();
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
	
	//Queries
	
}
