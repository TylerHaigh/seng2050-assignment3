package rgms.datacontext;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.logging.*;

import rgms.model.Document;
import rgms.model.Group;
import rgms.model.User;

/**
 * Provides the functionality to communicate with the database and perform queries
 * pertaining to Groups
 * 
 * @author Tyler Haigh - C3182929
 * @author Simon Hartcher - C3185790
 * @author Josh Crompton - C3165877
 *
 */
public class GroupManager extends DataManager {

	 private static final Logger logger = Logger.getLogger(GroupManager.class.getName());
	 
	 /**
	  * Creates a Group in the database
	  * @param group The Group to insert
	  */
	 public void createGroup(Group group) {
		 try {
			 //Create a prepare statement
			 PreparedStatement pstmt = conn.prepareStatement(
					 "INSERT INTO Groups (GroupName, Description, CoordinatorId) " +
					 "VALUES (?, ?, ?)"
			 );
			 
			 //Set the required parameters and execute
			 pstmt.setString(1, group.getGroupName());
			 pstmt.setString(2, group.getDescription());
			 pstmt.setInt(3, group.getCoordinatorId());
			 pstmt.execute();
			 
		 } catch (Exception e) {
			 logger.log(Level.SEVERE, "SQL Error", e);
		 }
	 }
	 
	 /**
	  * Creates a mapping between a Group and a User
	  * @param groupId The Id of the Group
	  * @param userId The Id of the User
	  */
	 public void createMapping(int groupId, int userId) {
		 try {
			 //Create a prepared statement
			 PreparedStatement pstmt = conn.prepareStatement(
				 "INSERT INTO GroupUserMaps (GroupId, UserId) " +
				 "VALUES (?,?)");
			 
			 //Set the required parameters and execute
			 pstmt.setInt(1, groupId);
			 pstmt.setInt(2, userId);
			 pstmt.execute();
			 
		 } catch (Exception e) {
			 logger.log(Level.SEVERE, "SQL Error", e);
		 } 
	 }
	 
	 /**
	  * Retrieves a Group with the given Id
	  * 
	  * @param groupId The Id of the Group
	  * @return A Group with the given Id
	  */
	 public Group get(int groupId) {
		 try {
			 //Create a prepared statement
			 PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Groups WHERE Id = ?");
			 
			 //Set the required parameters and execute
			 pstmt.setInt(1, groupId);
			 ResultSet rs = pstmt.executeQuery();
			 return Group.fromResultSet(rs);
			 
		 } catch (Exception e) {
			 logger.log(Level.SEVERE, "SQL Error", e);
			 return null;
		 } 
	 }

	 /**
	  * Retrieves a List of every Group that exists in the database
	  * @return A List containing every Group
	  */
	 public List<Group> getEveryGroup() {
		 List<Group> groups = new LinkedList<Group>();
		 
		 try {
			 //Create a prepared connection
			 PreparedStatement pstmt = conn.prepareStatement(
					 "SELECT * FROM Groups");
			 
			 //Execute the query
			 ResultSet rs = pstmt.executeQuery();
			 
			 //Retrieve the results and add to the list
			 if (rs.isBeforeFirst()) {
				 while (!rs.isAfterLast()) {
					Group resultGroup = Group.fromResultSet(rs);
					if (resultGroup != null)
						groups.add(resultGroup);
				 }
			 }
			 
		 } catch (Exception e) {
			 logger.log(Level.SEVERE, "SQL Error", e);
			 return null;
			 
		 } 
		 
		 return groups;
	 }
	 
	 /**
	  * Retrieves a List of all Groups that a User belongs to
	  * @param userId The Id of the User
	  * @return A List of Groups that the User belongs to
	  */
	 public List<Group> getAllGroups(int userId) {
		 List<Group> groups = new LinkedList<Group>();
		 
		 try {
			 //Create a prepared statement
			 PreparedStatement pstmt = conn.prepareStatement(
					 "SELECT g.* FROM Groups g " +
					 "JOIN GroupUserMaps m ON g.Id=m.GroupId " +
					 "JOIN Users u ON m.UserId=u.Id " +
					 "WHERE u.Id = ?");
			 
			 //Set the required parameters and execute
			 pstmt.setInt(1, userId);
			 ResultSet rs = pstmt.executeQuery();
			 
			 //Retrieve the results and add to the list
			 if (rs.isBeforeFirst()) {
				 while (!rs.isAfterLast()) {
					Group resultGroup = Group.fromResultSet(rs);
					if (resultGroup != null)
						groups.add(resultGroup);
				 }
			 }
			 
		 } catch (Exception e) {
			 logger.log(Level.SEVERE, "SQL Error", e);
			 return null;
			 
		 } 
		 
		 return groups;
	 }
	 
	 /**
	  * Retrieves a List of all Users in a Group
	  * @param groupId The Id of the Group
	  * @return A List of all Users in the Group
	  */
	 public List<User> getGroupUsers(int groupId) {
		 List<User> groupUsers = new LinkedList<User>();
		 
		 try {
			 //Create a prepared statement
			 PreparedStatement pstmt = conn.prepareStatement(
				 "SELECT u.* FROM Users u " +
				 "JOIN GroupUserMaps m ON u.Id=m.userId " +
				 "WHERE m.groupId = ?"
			 );
			 
			 //Set the required parameters and execute
			 pstmt.setInt(1, groupId);
			 ResultSet rs = pstmt.executeQuery();
			 
			 //Retrieve the results and store in the list
			 if (rs.isBeforeFirst()) {
				 while (!rs.isAfterLast()) {
					 User user = User.fromResultSet(rs);
					 if (user != null)
						 groupUsers.add(user);
				 }
			 }
			 
		 } catch (Exception e) {
			 logger.log(Level.SEVERE, "SQL Error", e);
		 } 
		 
		 return groupUsers;
	 }
	 
	 /**
	  * Retrieves a List of all UserNames of Users that belong to a Group
	  * @param groupId The Id of the Group
	  * @return A List of UserNames 
	  */
	 public List<String> getGroupMembers(int groupId){
		 List<String> usersInGroup = new LinkedList<String>();
		 
		 try {
			 //Create a Prepared Statement
			 PreparedStatement pstmt = conn.prepareStatement(
					 "SELECT UserName FROM Users u " +
					 "JOIN GroupUserMaps m ON u.Id=m.UserId " +
					 "WHERE m.GroupId = ?");
			
			 //Set the required parameters and execute
			 pstmt.setInt(1, groupId);
			 ResultSet rs = pstmt.executeQuery();
			 
			 //Retrieve the result and store in the list
			 if (rs.isBeforeFirst()) {
				 while (!rs.isAfterLast()) {
					//Group resultGroup = Group.fromResultSet(rs);
					 if (rs.next()) {
						 String uname = rs.getString("UserName");
						 if (uname != null)
								usersInGroup.add(uname);
						 }
					 }				
			 }
			 
		 } catch (Exception e) {
			 logger.log(Level.SEVERE, "SQL Error", e);
			 return null;
			 
		 } 

		 return usersInGroup;
	 }
	 
	 /**
	  * Retrieves the Id for a Group
	  * @param group The Group to query against
	  * @return The Id of the Group
	  */
	 public int getIdFor(Group group) {
		 try {
			 //Create a Prepared Statement
			 PreparedStatement pstmt = conn.prepareStatement(
				 "SELECT * FROM Groups " +
				 "WHERE GroupName=? AND Description=?");
			 
			 //Set the required parameters and execute
			 pstmt.setString(1, group.getGroupName());
			 pstmt.setString(2, group.getDescription());
			 ResultSet rs = pstmt.executeQuery();
			 
			 //Retrieve the result
			 if (rs.first()) {
				 int groupId = rs.getInt("Id");
				 return groupId;
			 }
		 } catch (Exception e) {
			 logger.log(Level.SEVERE, "SQL Error", e);
		 } 
 
		 return -1;
	 }

	 /**
	  * Retrieves the Group Coordinator for a given Group
	  * @param groupId The Id of the Group
	  * @return The Coordinator for the Group
	  */
	 public User getCoordinator(int groupId) {
		 try {
			 //Create a prepared statement
			 PreparedStatement pstmt = conn.prepareStatement(
				 "SELECT u.* FROM Users u " +
				 "JOIN GroupUserMaps m ON u.Id=m.UserId " +
				 "JOIN Groups g ON g.Id=m.GroupId " + 
				 "WHERE m.GroupId = ? " +
				 "AND u.Id=g.CoordinatorId");
			 
			 //Set the required parameters and execute
			 pstmt.setInt(1, groupId);
			 ResultSet rs = pstmt.executeQuery();
			 
			 return User.fromResultSet(rs);
		 } catch (Exception e) {
			 logger.log(Level.SEVERE, "SQL Error", e);
			 return null;
		 } 
	 }
	 
	 /**
	  * Deletes a mapping between a User and Group
	  * @param groupId The Id of the Group
	  * @param userId The Id of the User
	  */
	 public void removeMapping(int groupId, int userId) {
		 try {
			 //Create a prepared statement
			 PreparedStatement pstmt = conn.prepareStatement(
				 "DELETE FROM GroupUserMaps " +
				 "WHERE GroupId=? and UserId=?");
			 
			 //Set the required parameters and execute
			 pstmt.setInt(1, groupId);
			 pstmt.setInt(2, userId);
			 pstmt.execute();
			 
		 } catch (Exception e) {
			 logger.log(Level.SEVERE, "SQL Error", e);
		 } 
	 }
	 
}
