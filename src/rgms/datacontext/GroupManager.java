package rgms.datacontext;

import java.sql.*;
import java.util.*;
import java.util.logging.*;

import rgms.model.Document;
import rgms.model.Group;
import rgms.model.User;

public class GroupManager extends DataManager {

	 private static final Logger logger = Logger.getLogger(GroupManager.class.getName());
	 
	 public void createGroup(Group group) {
		 try {
			 Connection conn = connection.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(
					 "INSERT INTO Groups (GroupName, Description) " +
					 "VALUES (?, ?)"
			);
			 
			 pstmt.setString(1, group.getGroupName());
			 pstmt.setString(2, group.getDescription());
					 
			 pstmt.execute();
			 
		 } catch (Exception e) {
			 logger.log(Level.SEVERE, "SQL Error", e);
		 }
	 }
	 
	 public Group get(int groupId) {
		 Connection conn = null;
		 try {
			 conn = connection.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Groups WHERE Id = ?");
			 pstmt.setInt(1, groupId);
			 
			 ResultSet rs = pstmt.executeQuery();
			 return Group.fromResultSet(rs);
		 } catch (Exception e) {
			 logger.log(Level.SEVERE, "SQL Error", e);
			 return null;
		 } finally {
			 if (conn != null) {
				 try {
					 conn.close();
				 } catch (SQLException e) {
					 logger.log(Level.WARNING, "Connection Close", e);
				 }
			 }
		 }
	 }

	 public List<Group> getAllGroups(int userId) {
		 Connection conn = null;
		 List<Group> groups = new LinkedList<Group>();
		 
		 try {
			 conn = connection.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(
					 "SELECT * FROM Groups g " +
					 "JOIN GroupUserMaps m ON g.Id=m.GroupId " +
					 "JOIN Users u ON m.UserId=u.Id " +
					 "WHERE u.Id = ?");
			 
			 pstmt.setInt(1, userId);
			 
			 ResultSet rs = pstmt.executeQuery();
			 
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
			 
		 } finally {
			 if (conn != null) {
				 try {
					 conn.close();
				 } catch (SQLException e) {
					 logger.log(Level.WARNING, "Connection Close", e);
				 }
			 }
		 }
		 
		 return groups;
	 }
	 
	 public List<User> getGroupUsers(int groupId) {
		 Connection conn = null;
		 List<User> groupUsers = new LinkedList<User>();
		 
		 try {
			 conn = connection.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(
				 "SELECT * FROM Users u " +
				 "JOIN GroupUserMaps m ON u.Id=m.userId " +
				 "WHERE m.groupId = ?"
			 );
			 
			 pstmt.setInt(1, groupId);
			 
			 ResultSet rs = pstmt.executeQuery();
			 
			 if (rs.isBeforeFirst()) {
				 while (!rs.isAfterLast()) {
					 User user = User.fromResultSet(rs);
					 if (user != null)
						 groupUsers.add(user);
				 }
			 }
		 } catch (Exception e) {
			 logger.log(Level.SEVERE, "SQL Error", e);
		 } finally {
			 if (conn != null) { 
				 try {
					 conn.close();
				 } catch (SQLException e) {
					 logger.log(Level.WARNING, "Connection Close", e);
				 }
			 }
		 }
		 
		 return groupUsers;
	 }
	 
	 public List<String> getGroupMembers(String groupId){
		 List<String> usersInGroup = new LinkedList<String>();
		 Connection conn = null;
		 int groupNum = Integer.parseInt(groupId);
		 try {
			 conn = connection.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(
					 "SELECT userName FROM Users u " +
					 "JOIN GroupUserMaps m ON u.Id=m.userId " +
					 "WHERE m.groupId = ?");
			
			 pstmt.setInt(1, groupNum);
			 
			 ResultSet rs = pstmt.executeQuery();
			 
			 if (rs.isBeforeFirst()) {
				 while (!rs.isAfterLast()) {
					//Group resultGroup = Group.fromResultSet(rs);
					 if (rs.next()) {
						 String uname = rs.getString("userName");
						 if (uname != null)
								usersInGroup.add(uname);
						 }
					 }				
			 }
			 
		 } catch (Exception e) {
			 logger.log(Level.SEVERE, "SQL Error", e);
			 return null;
			 
		 } finally {
			 if (conn != null) {
				 try {
					 conn.close();
				 } catch (SQLException e) {
					 logger.log(Level.WARNING, "Connection Close", e);
				 }
			 }
		 }
		 return usersInGroup;
	 }
	 public List<Document> getGroupDocuments(String groupId){
		 List<Document> groupDocuments = new LinkedList<Document>();
		 Connection conn = null;
		 int groupNum = Integer.parseInt(groupId);
		 try {
			 conn = connection.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(
					 "SELECT * FROM documents " +
					 "WHERE groupId = ?");
			
			 pstmt.setInt(1, groupNum);
			 
			 ResultSet rs = pstmt.executeQuery();
			 
			 if (rs.isBeforeFirst()) {
				 while (!rs.isAfterLast()) {
					 //This may throw null pointer exception if there are no documents
					 //if (rs.next()) {
					 	Document d = Document.fromResultSet(rs);
					 	if(d != null){
					 		groupDocuments.add(d);
					 	}						 
					// }				
				 }
			 }
			 
		 } catch (Exception e) {
			 logger.log(Level.SEVERE, "SQL Error", e);
			 return null;
			 
		 } finally {
			 if (conn != null) {
				 try {
					 conn.close();
				 } catch (SQLException e) {
					 logger.log(Level.WARNING, "Connection Close", e);
				 }
			 }
		 }
		 return groupDocuments;
	 }
	 public Document getDocument(int documentId){
		 Document aDocument = new Document();
		 Connection conn = null;
		 try {
			 conn = connection.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(
					 "SELECT * FROM documents " +
					 "WHERE id = ?");
			
			 pstmt.setInt(1, documentId);
			 
			 ResultSet rs = pstmt.executeQuery();
			 
			 if (rs.isBeforeFirst()) {
				 while (!rs.isAfterLast()) {
					 //This may throw null pointer exception if there are no documents
					 //if (rs.next()) {
					 	aDocument = Document.fromResultSet(rs);
					 	return aDocument;
					// }				
				 }
			 }
			 
		 } catch (Exception e) {
			 logger.log(Level.SEVERE, "SQL Error", e);
			 return null;
			 
		 } finally {
			 if (conn != null) {
				 try {
					 conn.close();
				 } catch (SQLException e) {
					 logger.log(Level.WARNING, "Connection Close", e);
				 }
			 }
		 }
		 return aDocument;
	 }
	 
	 
}
