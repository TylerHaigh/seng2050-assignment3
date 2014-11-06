package rgms.datacontext;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.*;

import rgms.model.User;
import rgms.infrastructure.*;

/**
 * Provides the functionality to communicate with the database and perform queries
 * pertaining to Users
 * 
 * @author Tyler Haigh - C3182929
 * @author Simon Hartcher - C3185790
 * @author Josh Crompton - C3165877
 *
 */
public class UserManager extends DataManager {
  private static final Logger logger = Logger.getLogger(UserManager.class.getName());

  /**
   * Default constructor for a User Manager. Establishes a connection to the database
   */
  public UserManager() { 
    super();
  }
  
  /**
   * Constructor for a User Manager. Sets the database connection to the parameterised
   * connection
   * 
   * @param connection A Database connection
   */
  public UserManager(Connection connection) {
    this.connection = new JDBCConnection(connection);
  }
    
  /**
   * Creates a User in the database
   * @param user The User to insert
   * @param plainPassword An unhashed version of the User's password
   */
  public void createUser(User user, String plainPassword) {
    String hashedPass = UserManager.hashPassword(plainPassword);
    user.setPassword(hashedPass);

    try {
      Connection conn = connection.getConnection();
      
      //Create a Prepared Statement
      PreparedStatement pstmt = conn.prepareStatement(
        "INSERT INTO Users (FirstName, LastName, UserName, Passphrase, StudentId)" +
        "VALUES (?, ?, ?, ?, ?)"
        );

      //Set the required parameters and execute
      pstmt.setString(1, user.getFirstName());
      pstmt.setString(2, user.getLastName());
      pstmt.setString(3, user.getUserName());
      pstmt.setString(4, user.getPassword());
      pstmt.setString(5, user.getStudentId());

      pstmt.execute();
    }
    catch (Exception e) {
      logger.log(Level.SEVERE, "SQL Error", e);
    }
  }
  
  /**
   * Updates a User in the database
   * @param user The User to update
   * @param plainPassword An unhashed version of the User's password
   */
  public void updateUser(User user, String plainPassword) {
	  String hashedPass = UserManager.hashPassword(plainPassword);
	   user.setPassword(hashedPass);
	  
	   //connect to db
	  try { 
		  Connection conn = connection.getConnection();
		  
		  //Create a Prepared statement
		  PreparedStatement pstmt = conn.prepareStatement(
		  	"UPDATE users " + 
		  	"SET FirstName=?, LastName=?, Passphrase=?, ImageReference = ?" + 
		  	"WHERE Username=? ;"
		  	);
		  
		  //Set the required parameters and execute
		  pstmt.setString(1, user.getFirstName());
		  pstmt.setString(2, user.getLastName());
		  pstmt.setString(3, user.getPassword());
		  pstmt.setString(4, user.getImageReference());
		  pstmt.setString(5, user.getUserName());
		  
		  pstmt.execute();
	  }
	  catch (Exception e) {
		  logger.log(Level.SEVERE, "SQL Error, Updating table", e);
	  }
  }
  
  /**
   * Updates a User in the database
   * @param user The user to update
   */
  public void updateUser(User user) {
    //connect to db
    try { 
      Connection conn = connection.getConnection();
      
      //Create a prepared statement
      PreparedStatement pstmt = conn.prepareStatement(
        "UPDATE users " + 
        "SET FirstName=?, LastName=?, ImageReference = ?" + 
        "WHERE Id=? ;"
        );
      
      //Set the required parameters and execute
      pstmt.setString(1, user.getFirstName());
      pstmt.setString(2, user.getLastName());
      pstmt.setString(3, user.getImageReference());
      pstmt.setInt(4, user.getId());
      
      pstmt.execute();
    }
    catch (Exception e) {
      logger.log(Level.SEVERE, "SQL Error, Updating table", e);
    }
  }

  /**
   * Activates a User's account in the database
   * @param userId The Id of the User
   */
  public void activateUser(int userId) {
	  try {
		  Connection conn = connection.getConnection();
		  
		  //Create a prepared statement
		  PreparedStatement pstmt = conn.prepareStatement(
			  "UPDATE Users SET IsActive=true WHERE Id=?"
		  );
		  
		  //Set the required parameters and execute
		  pstmt.setInt(1, userId);
		  pstmt.execute();
		  
	  } catch (SQLException e) {
		  logger.log(Level.SEVERE, "SQL Error", e);
	  }
  }
  
  /**
   * Retrieves a User with the given Id
   * @param id The Id of the User
   * @return A User with the given Id
   */
  public User get(int id) {
    Connection conn = null;
    try {
      conn = connection.getConnection();
     
      //Create a prepared statement
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Users WHERE Id = ?");
      
      //Set the required parameters and execute
      pstmt.setInt(1, id);
      ResultSet rs = pstmt.executeQuery();
      return User.fromResultSet(rs);
    }
    catch (Exception e) {
      logger.log(Level.SEVERE, "SQL Error", e);
      return null;
    }
  }

  /**
   * Retrieves a User with the given UserName
   * @param userName the UserName of the User
   * @return The User with the UserName
   */
  public User get(String userName) {
    Connection conn = null;
    try {
      conn = connection.getConnection();
      
      //Create a prepared statement
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Users WHERE Username = ?");
      
      //Set the requires parameters and execute
      pstmt.setString(1, userName);
      ResultSet rs = pstmt.executeQuery();
      return User.fromResultSet(rs);
      
    }
    catch (Exception e) {
      logger.log(Level.SEVERE, "SQL Error", e);
      return null;
    }
    finally {
      if (conn != null)
        try {
          conn.close();
        }
        catch (SQLException e) {
          logger.log(Level.WARNING, "Connection Close", e);
        }
    }
  }

  /**
   * Retrieves a List of all Coordinators
   * @return A List of all Coordinators
   */
  public List<User> getCoordinators() {
	  Connection conn = null;
	  List<User> coordinators = new LinkedList<User>();
	  
	  try {
		  conn = connection.getConnection();
		  
		  //Create a prepared statement
		  PreparedStatement pstmt = conn.prepareStatement(
			  "SELECT * FROM Users WHERE IsAdmin=true"
		  );
		  
		  //Execute the query
		  ResultSet rs = pstmt.executeQuery();
		  
		  //Retrieve the results and store in the list
		  if (rs.isBeforeFirst()) {
			  while (!rs.isAfterLast()) {
				  User user = User.fromResultSet(rs);
				  if (user != null)
					  coordinators.add(user);
			  }
		  }
	  } catch (SQLException e) {
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
	  
	  return coordinators;
  }
  
  /**
   * Retrieves a List containing details of every User in the database
   * @return A List of all Users
   */
  public List<User> getEveryUser() {
	  Connection conn = null;
	  List<User> users = new LinkedList<User>();
	  
	  try {
		  conn = connection.getConnection();
		  
		  //Create a prepared statement
		  PreparedStatement pstmt = conn.prepareStatement(
			  "SELECT * FROM Users"
		  );
		  
		  //Execute the Query
		  ResultSet rs = pstmt.executeQuery();
		  
		  //Retrieve the results and store in the List
		  if (rs.isBeforeFirst()) {
			  while (!rs.isAfterLast()) {
				  User user = User.fromResultSet(rs);
				  if (user != null)
					  users.add(user);
			  }
		  }
	  } catch (SQLException e) {
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
	  
	  return users;
  }
  
  /**
   * Validates a User's password for authentication
   * @param userName The User's UserName
   * @param plainPassword An unhashed version of the User's password
   * @return True if the plainPassword is correct, false otherwise
   */
  public boolean validate(String userName, String plainPassword) {
    User user = this.get(userName);
    String hashedPass = UserManager.hashPassword(plainPassword);
    boolean valid = hashedPass.equals(user.getPassword());
    
    if (!valid) {
      logger.log(Level.INFO, "User Validation Failed. Expected: " + 
        user.getPassword() +
        " Actual: " +
        hashedPass);
    }

    return valid;
  }

  /**
   * Creates a hash of a User's password
   * @param password The plain password to hash
   * @return The hashed password
   */
  public static String hashPassword(String password) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(password.getBytes());

      byte bytes[] = md.digest();

      //get hashed string
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < bytes.length; i++) {
       sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
      }

      return sb.toString();
    }
    catch (NoSuchAlgorithmException e) { 
      //todo: handle this
      return null;
    }
  }
}