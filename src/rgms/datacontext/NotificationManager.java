package rgms.datacontext;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.*;

import rgms.model.*;

public class NotificationManager extends DataManager {

	 private static final Logger logger = Logger.getLogger(NotificationManager.class.getName());
	 
	 public void createNotification(Notification notification) {
		 try {
			 Connection conn = connection.getConnection();
			 String preparedStatementString = createPreparedStatementString(notification);
			 PreparedStatement pstmt = conn.prepareStatement(preparedStatementString);
			 pstmt.execute();
			 
		 } catch (SQLException sql) {
			 logger.log(Level.SEVERE, "SQL Error",sql);
		 } catch (Exception e) {
			 logger.log(Level.SEVERE, "Error executing query",e);
		 }
	 }
	 
	 public void deleteNotification(int notificationId) {
		 try {
			 Connection conn = connection.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(
				 "DELETE FROM Notifications WHERE Id=?"
			 );
			 
			 pstmt.setInt(1, notificationId);
			 pstmt.execute();
		 } catch (SQLException e) {
			 logger.log(Level.SEVERE, "SQL Error",e);
		 }
	 }
	 
	 public Notification get(int notificationId) {
		 Connection conn = null;
		 
		 try {
			 conn = connection.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(
				 "SELECT * FROM Notifications WHERE Id=?"
			 );
			 
			 pstmt.setInt(1, notificationId);
			 ResultSet rs = pstmt.executeQuery();
			 return Notification.fromResultSet(rs);
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
	 
	 public List<Notification> getAllNotifications(int userId) {
		 Connection conn = null;
		 List<Notification> notifications = new LinkedList<Notification>();
		 
		 try {
			 conn = connection.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(
				 "SELECT * FROM Notifications n " +
				 "JOIN Users u ON n.UserId=u.Id " +
				 "WHERE u.Id=?"
			 );
			 
			 pstmt.setInt(1, userId);
			 ResultSet rs = pstmt.executeQuery();
			 
			 if (rs.isBeforeFirst()) {
				 
				 while (!rs.isAfterLast()) {
					 Notification resultNotification = Notification.fromResultSet(rs);
					 if (resultNotification != null)
						 notifications.add(resultNotification);
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
		 
		 return notifications;
 	 }
	 
 	 private String createPreparedStatementString(Notification notification) throws Exception {
		 
		 String query = "INSERT INTO Notifications ";
		 NotificationType type = notification.getNotificationType();
		 
		 if (type == NotificationType.RegisteringUser) {
			 query += String.format(
				"(UserId, NotificationTypeId, RegisteringUserId, Description) " +
				"VALUES ({0}, {1}, {2}, {3})",
				notification.getUserId(), 1,
				notification.getRegisteringUserId(), "\'User wants to join\'");
		 } else if (type == NotificationType.Meeting) {
			 query += String.format(
				"(UserId, GroupId, NotificationTypeId, MeetingId, Description) " +
				"VALUES ({0}, {1}, {2}, {3})",
				notification.getUserId(), notification.getGroupId(), 2,
				notification.getMeetingId(), "\'A meeting has been created\'");
		 } else if (type == NotificationType.Document) {
			 query += String.format(
				"(UserId, GroupId, NotificationTypeId, DocumentId, Description) " +
				"VALUES ({0}, {1}, {2}, {3})",
				notification.getUserId(), notification.getGroupId(), 3,
				notification.getDocumentId(), "\'A document has been added\'");
		 } else if (type == NotificationType.DiscussionPost) {
			 query += String.format(
				"(UserId, GroupId, NotificationTypeId, DiscussionPostId, Description) " +
				"VALUES ({0}, {1}, {2}, {3})",
				notification.getUserId(), notification.getGroupId(), 4,
				notification.getDiscussionPostId(), "\'A new discussion post has been added\'");
		 } else {
			 throw new Exception("Not a valid notification");
		 }
		 
		 return query;
	 }

	 
}
