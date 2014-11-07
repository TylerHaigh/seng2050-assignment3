package rgms.model;

import java.io.Serializable;
import java.sql.*;
import java.util.logging.*;

public class Group implements Serializable {

	//Private Instance Variables
	private int id;
	private String groupName;
	private String description;
	private int coordinatorId;
	
	//Constructors
	
	public Group() {
		this.id = 0;
		this.groupName = "";
		this.description = "";
		this.coordinatorId = 0;
	}
	
	public Group(int id, String groupName, String description, int coordinatorId) {
		this.id = id;
		this.groupName = groupName;
		this.description = description;
		this.coordinatorId = coordinatorId;
	}

	//Getters
	
	public int getId() {
		return id;
	}

	public String getGroupName() {
		return groupName;
	}
	
	public String getDescription() {
		return description;
	}

	public int getCoordinatorId(){
		return coordinatorId;
	}
	//Setters
	
	public void setId(int id) {
		this.id = id;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setCoordinatorId(int id){
		coordinatorId = id;
	}
	
	//Queries
	
	public static Group fromResultSet(ResultSet rs) {
		Group group = null;
		
		try {
			if (rs.next()) {
				group = new Group();
				group.setId(rs.getInt("Id"));
				group.setGroupName(rs.getString("GroupName"));
				group.setDescription(rs.getString("Description"));
				group.setCoordinatorId(rs.getInt("CoordinatorId"));
				
				Logger.getLogger("rgms.model.Group").info(
					String.format("Loaded Group: %d, %s, %s",
						group.getId(), group.getGroupName(), group.getDescription())
				);
				
			}
		} catch (SQLException e) {
			Logger.getLogger("rgms.model.Group").log(Level.SEVERE, "SQL Error", e);
		}
		
		return group;
	}
	
}
