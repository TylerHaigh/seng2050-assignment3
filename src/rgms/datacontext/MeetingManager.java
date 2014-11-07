package rgms.datacontext;

import java.sql.*;
import java.sql.Date;

import java.text.SimpleDateFormat;

import java.text.*;

import java.util.*;
import java.util.logging.*;

import rgms.model.Meeting;

/**
 * Provides the functionality to communicate with the database and perform queries
 * pertaining to Meetings
 * 
 * @author Tyler Haigh - C3182929
 * @author Simon Hartcher - C3185790
 * @author Josh Crompton - C3165877
 *
 */
public class MeetingManager extends DataManager {

	 private static final Logger logger = Logger.getLogger(MeetingManager.class.getName());
	 
	 /**
	  * Creates a Meeting in the database
	  * @param meeting The Meeting to insert
	  */
	 public void createMeeting(Meeting meeting) {
		 try {
			 Connection conn = connection.getConnection();
			 
			 //Create a prepared statement
			 PreparedStatement pstmt = conn.prepareStatement(
					 "INSERT INTO Meetings (Description, CreatedByUserId, DateCreated, DateDue, GroupId) " +
					 "VALUES (?, ?, ?, ?, ?)"
			);
			 
			 /*
			  * MySQL truncates the time component of java.util.Date
			  * This conversion will allow correct insertion into the database 
			  */
			 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 String creationDateString = dateFormat.format(meeting.getDateCreated());
			 String dueDateString = dateFormat.format(meeting.getDateDue());
			 
			 //Set the required parameters and execute
			 pstmt.setString(1, meeting.getDescription());
			 pstmt.setInt(2, meeting.getCreatedByUserId());
			 pstmt.setString(3, creationDateString);
			 pstmt.setString(4, dueDateString);
			 pstmt.setInt(5, meeting.getGroupId());		 
			 pstmt.execute();
			 
		 } catch (Exception e) {
			 logger.log(Level.SEVERE, "SQL Error", e);
		 }
	 }
	 
	 /**
	  * Retrieves a Meeting with the given Id
	  * @param meetingId The Id of the Meeting
	  * @return A Meeting with the given Id
	  */
	 public Meeting get(int meetingId) {
		 Connection conn = null;
		 
		 try {
			 conn = connection.getConnection();
			 
			 //Create a Prepared Statement
			 PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Meetings WHERE Id = ?");
			 
			 //Set the required parameters and execute
			 pstmt.setInt(1, meetingId);
			 ResultSet rs = pstmt.executeQuery();
			 return Meeting.fromResultSet(rs);
			 
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
	 
	 /**
	  * Retrieves the Id for a Meeting
	  * @param meeting The Meeting to query against
	  * @return The Id of the Meeting
	  */
	 public int getIdFor(Meeting meeting) {
		 Connection conn = null;
		 try {
			 conn = connection.getConnection();
			 
			 //Create a prepared statement
			 PreparedStatement pstmt = conn.prepareStatement(
				 "SELECT * FROM Meetings " +
				 "WHERE Description=? AND CreatedByUserId=? " + 
				 "AND DateCreated=? AND DateDue=? and GroupId=?");
			 
			 /*
			  * MySQL truncates Date information. This will resolve the issue
			  */
			 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 String creationDateString = dateFormat.format(meeting.getDateCreated());
			 String dueDateString = dateFormat.format(meeting.getDateDue());
			 
			 //Set the required parameters and execute
			 pstmt.setString(1, meeting.getDescription());
			 pstmt.setInt(2, meeting.getCreatedByUserId());
			 pstmt.setString(3, creationDateString);
			 pstmt.setString(4, dueDateString);
			 pstmt.setInt(5, meeting.getGroupId());	
			 ResultSet rs = pstmt.executeQuery();
			 
			 //Retrieve the result
			 if (rs.first()) {
				 int meetingId = rs.getInt("Id");
				 return meetingId;
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
		 
		 return -1;
	 }
	 
	 /**
	  * Retrieves a List of Meetings that a User is attending
	  * @param userId The Id of the User
	  * @return A List of Meetings the User is attending
	  */
	 public List<Meeting> getAllMeetings(int userId) {
		 Connection conn = null;
		 List<Meeting> meetings = new LinkedList<Meeting>();
		 
		 //Date so only returns meetings that are yet to occur.
		 java.util.Date now = new java.util.Date();
		 java.sql.Date sqlDate = new java.sql.Date(now.getTime());
		 
		 try {
			 conn = connection.getConnection();
			 
			 //Create a prepared statement
			 PreparedStatement pstmt = conn.prepareStatement(
					 "SELECT meet.* FROM Meetings meet " +
					 "JOIN Groups g ON meet.GroupId=g.Id " +
					 "JOIN GroupUserMaps map ON g.Id=map.GroupId " +
					 "JOIN Users u ON map.UserId=u.Id " +
					 "WHERE u.Id = ? AND DateDue > ?");
			 
			 //Set the required parameters and execute
			 pstmt.setInt(1, userId);
			 pstmt.setDate(2, sqlDate);
			 ResultSet rs = pstmt.executeQuery();

			 //Retrieve the result an add to the List
			 if (rs.isBeforeFirst()) {
				 while (!rs.isAfterLast()) {
					Meeting resultMeeting = Meeting.fromResultSet(rs);
					if (resultMeeting != null)
						meetings.add(resultMeeting);
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
		 
		 return meetings;
	 }

	 /**
	  * Retrieves a List of Meetings that belong to a Group
	  * @param groupId The Id of the Group
	  * @return A List of Meetings that belong to the Group
	  */
	 public List<Meeting> getGroupMeetings(int groupId) {
		 Connection conn = null;
		 List<Meeting> meetings = new LinkedList<Meeting>();
		 try {
			 //Connect to Database
			 conn = connection.getConnection();
			 
			 //Only want meetings in the future.
			 java.util.Date now = new java.util.Date();
			 java.sql.Date sqlDate = new java.sql.Date(now.getTime());
			 
			 //Create a prepared statement
			 PreparedStatement pstmt = conn.prepareStatement(
					 "SELECT * FROM Meetings meet " +
					 "WHERE GroupId = ? AND DateDue > ?");
			 
			 //Set the required parameters and execute 
			 pstmt.setInt(1, groupId);
			 pstmt.setDate(2, sqlDate);
			 ResultSet rs = pstmt.executeQuery();
			 
			 if (rs.isBeforeFirst()) {
				 //Get all meetings from result set.
				 while (!rs.isAfterLast()) {
					Meeting resultMeeting = Meeting.fromResultSet(rs);
					if (resultMeeting != null)
						meetings.add(resultMeeting);
				 }
			 }
			 
		 } catch (Exception e) {
			 logger.log(Level.SEVERE, "SQL Error", e);
			 return null;
		 }
		 return meetings;
	 }
	 
	 /**
	  * Deletes a Meeting from the database
	  * @param meetingId The Id of the Meeting 
	  */
	 public void deleteMeeting(int meetingId) {
		 Connection conn = null;
		 try {
			 conn = connection.getConnection();
			 
			 //Create a Prepared Statement
			 PreparedStatement pstmt = conn.prepareStatement(
				"DELETE FROM Meetings WHERE Id=?"	 
			 );
			 
			 //Set the requires parameters and execute
			 pstmt.setInt(1, meetingId);
			 pstmt.execute();
			 
		 } catch (SQLException e) {
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
	 }

}
