package rgms.controller;

import java.io.Serializable;
import java.sql.*;
import java.util.LinkedList;
import java.util.logging.*;

import rgms.infrastructure.*;
import rgms.model.*;
import rgms.datacontext.UserManager;

@SuppressWarnings("serial")
public class ProfileController implements Serializable {

	private static final Logger logger = Logger.getLogger(ProfileController.class.getName());
	
	public User getProfileUser(String username) {
		UserManager userManager = new UserManager();
		return userManager.get(username);
	}
	
	public LinkedList<Group> getGroups(String username) {
		LinkedList<Group> groups = new LinkedList<Group>();
		
		try {
			JDBCConnection connection = new JDBCConnection();
			
			String query = "SELECT g.Id, g.GroupName FROM Groups g " + 
					"JOIN GroupUserMaps m ON g.Id=m.GroupId " + 
					"JOIN Users u ON m.UserId=u.Id " +
					"WHERE u.Username='" + username + "'";
			
			try {
				connection.getConnection();
				ResultSet rs = connection.executeQuery(query);
				
	            while (rs != null && rs.next()) {
	            	groups.add(makeGroup(rs));
	            }
	            
	            connection.setDone();
				
			} catch (SQLException sql) {
				logger.log(Level.SEVERE, "Unable to execute query: " + query, sql);
			}
			
			
		} catch (Exception jdbc) {
			logger.log(Level.SEVERE, "Unable to establish a connection to the JDBC", jdbc);
		}
		
		return groups;
	}
	
	private Group makeGroup(ResultSet rs) throws NumberFormatException, SQLException {
		int id = Integer.parseInt(rs.getString("Id"));
		String groupName = rs.getString("GroupName");
		
		Group g = new Group(id, groupName);
		return g;
	}
}
