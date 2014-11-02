package rgms.model;

import java.io.Serializable;
import java.sql.*;
import java.util.logging.*;

public class Group implements Serializable {

	//Private Instance Variables
	private int id;
	private String groupName;
	private String description;
	
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
	
	public String getDescription() {
		return description;
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
	
	//Queries
	
	public static Group fromResultSet(ResultSet rs) {
		Group group = null;
		
		try {
			if (rs.next()) {
				group = new Group();
				group.setId(rs.getInt("Id"));
				group.setGroupName(rs.getString("GroupName"));
				group.setDescription(rs.getString("Description"));
				
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
