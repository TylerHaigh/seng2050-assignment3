package rgms.datacontext;

import java.sql.*;
import java.sql.Date;
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
			 
			 pstmt.setString(1, meeting.getDescription());
			 pstmt.setInt   (2, meeting.getCreatedByUserId());
			 pstmt.setDate  (3, (Date) meeting.getDateCreated());
			 pstmt.setDate  (4, (Date) meeting.getDateDue());
			 pstmt.setInt   (5, meeting.getGroupId());		 
			 
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
}