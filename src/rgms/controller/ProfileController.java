package rgms.controller;

import java.io.Serializable;
import java.sql.*;
import java.util.LinkedList;
import java.util.logging.*;

import rgms.infrastructure.*;
import rgms.model.*;

@SuppressWarnings("serial")
public class ProfileController implements Serializable {

	private JDBCConnection connection;
	private static final Logger logger = Logger.getLogger(ProfileController.class.getName());
	
	public ProfileController() {
		
	}
	
	public User getProfileUser(String username) {
		
		User thisUser = null;
		
		try {
			connection = new JDBCConnection();
			
			String query = "SELECT * FROM Users WHERE Username='" + username + "'";
			
			try {
				connection.getConnection();
				ResultSet rs = connection.executeQuery(query);
				
				/**
	             * Since usernames are unique, there should only be at most
	             * one set returned by the query
	             */
	            if (rs != null && rs.next()) {
	                thisUser = AuthenticationManager.CreateUser(rs);
	            }
	            
	            connection.setDone();
				
			} catch (SQLException sql) {
				logger.log(Level.SEVERE, "Unable to execute query: " + query, sql);
			}
			
			
		} catch (Exception jdbc) {
			logger.log(Level.SEVERE, "Unable to establish a connection to the JDBC", jdbc);
		}
		
		return thisUser;
	}
	
	public LinkedList<Group> getGroups(String username) {
		LinkedList<Group> groups = new LinkedList<Group>();
		
		try {
			connection = new JDBCConnection();
			
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
