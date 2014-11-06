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
			 PreparedStatement pstmt = conn.prepareStatement(
				 "INSERT INTO Notifications (UserId, GroupId, Description, Link) " +
				 "VALUES(?,?,?,?)"
			 );
			 
			 pstmt.setInt(1, notification.getUserId());
			 pstmt.setString(3, notification.getDescription());
			 pstmt.setString(4, notification.getLink());
			 
			 if (notification.getGroup() == null)
				 pstmt.setNull(2, Types.INTEGER);
			 else
				 pstmt.setInt(2, notification.getGroupId());

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
				 "SELECT n.* FROM Notifications n " +
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
	 
}
