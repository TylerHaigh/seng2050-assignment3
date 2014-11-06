package rgms.datacontext;

import java.sql.*;
import java.sql.Date;
import java.text.*;
import java.util.*;
import java.util.logging.*;

import rgms.model.Meeting;

public class MeetingManager extends DataManager {

	 private static final Logger logger = Logger.getLogger(MeetingManager.class.getName());
	 
	 public void createMeeting(Meeting meeting) {
		 try {
			 Connection conn = connection.getConnection();
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
	 
	 public Meeting get(int meetingId) {
		 Connection conn = null;
		 try {
			 conn = connection.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Meetings WHERE Id = ?");
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
	 
	 public int getIdFor(Meeting meeting) {
		 Connection conn = null;
		 try {
			 conn = connection.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(
				 "SELECT * FROM Meetings " +
				 "WHERE Description=? AND CreatedByUserId=? " + 
				 "AND DateCreated=? AND DateDue=? and GroupId=?");
			 
			 pstmt.setString(1, meeting.getDescription());
			 pstmt.setInt(2, meeting.getCreatedByUserId());
			 pstmt.setDate(3, new Date(meeting.getDateCreated().getTime()));
			 pstmt.setDate(4, new Date(meeting.getDateDue().getTime()));
			 pstmt.setInt(5, meeting.getGroupId());
			 
			 ResultSet rs = pstmt.executeQuery();
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
	 
	 public List<Meeting> getAllMeetings(int userId) {
		 Connection conn = null;
		 List<Meeting> meetings = new LinkedList<Meeting>();
		 
		 try {
			 conn = connection.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(
					 "SELECT * FROM Meetings meet " +
					 "JOIN Groups g ON meet.GroupId=g.Id " +
					 "JOIN GroupUserMaps map ON g.Id=map.GroupId " +
					 "JOIN Users u ON map.UserId=u.Id " +
					 "WHERE u.Id = ?");
			 
			 pstmt.setInt(1, userId);
			 
			 ResultSet rs = pstmt.executeQuery();
			 
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
	 
	 public void deleteMeeting(int meetingId) {
		 Connection conn = null;
		 try {
			 conn = connection.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(
				"DELETE FROM Meetings WHERE Id=?"	 
			 );
			 
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
