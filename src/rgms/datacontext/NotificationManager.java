package rgms.datacontext;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.*;

import rgms.model.*;

/**
 * Provides the functionality to communicate with the database and perform queries
 * pertaining to Notifications
 * 
 * @author Tyler Haigh - C3182929
 * @author Simon Hartcher - C3185790
 * @author Josh Crompton - C3165877
 *
 */
public class NotificationManager extends DataManager {

	 private static final Logger logger = Logger.getLogger(NotificationManager.class.getName());
	 
	 /**
	  * Creates a Notification in the database
	  * @param notification The Notification to insert
	  */
	 public void createNotification(Notification notification) {
		 try {
			 Connection conn = connection.getConnection();
			 
			 //Create a Prepared Statement
			 PreparedStatement pstmt = conn.prepareStatement(
				 "INSERT INTO Notifications (UserId, GroupId, Description, Link) " +
				 "VALUES(?,?,?,?)"
			 );
			 
			 //Set the required parameters and execute
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
	 
	 /**
	  * Deletes a Notification from the database
	  * @param notificationId The Id of the Notification
	  */
	 public void deleteNotification(int notificationId) {
		 try {
			 Connection conn = connection.getConnection();
			 
			 //Create a prepared statement
			 PreparedStatement pstmt = conn.prepareStatement(
				 "DELETE FROM Notifications WHERE Id=?"
			 );
			 
			 //Set the required parameters and execute
			 pstmt.setInt(1, notificationId);
			 pstmt.execute();
			 
		 } catch (SQLException e) {
			 logger.log(Level.SEVERE, "SQL Error",e);
		 }
	 }
	 
	 /**
	  * Retrieves a Notification with the given Id
	  * @param notificationId The Id of the Notification
	  * @return A Notification with the given Id
	  */
	 public Notification get(int notificationId) {
		 Connection conn = null;
		 
		 try {
			 conn = connection.getConnection();
			 
			 //Create a prepared statement
			 PreparedStatement pstmt = conn.prepareStatement(
				 "SELECT * FROM Notifications WHERE Id=?"
			 );
			 
			 //Set the required parameters and execute
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
	 
	 /**
	  * Retrieves a List of all Notifications that belong to a User
	  * @param userId The Id of the User
	  * @return A List of Notifications that belong to the User
	  */
	 public List<Notification> getAllNotifications(int userId) {
		 Connection conn = null;
		 List<Notification> notifications = new LinkedList<Notification>();
		 
		 try {
			 conn = connection.getConnection();
			 
			 //Create a prepared statement
			 PreparedStatement pstmt = conn.prepareStatement(
				 "SELECT n.* FROM Notifications n " +
				 "JOIN Users u ON n.UserId=u.Id " +
				 "WHERE u.Id=?"
			 );
			 
			 //Set the required parameters and execute
			 pstmt.setInt(1, userId);
			 ResultSet rs = pstmt.executeQuery();
			 
			 //Retrieve the results and store in the List
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
