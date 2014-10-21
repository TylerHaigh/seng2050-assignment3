package rgms.datacontext;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.logging.*;

import rgms.model.User;
import rgms.infrastructure.*;

public class UserManager extends DataManager {
  private static final Logger logger = Logger.getLogger(UserManager.class.getName());
    
  public void createUser(User user, String plainPassword) {
    String hashedPass = UserManager.hashPassword(plainPassword);
    user.setPassword(hashedPass);

    try {
      Connection conn = connection.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(
        "INSERT INTO Users (FirstName, LastName, UserName, Passphrase, StudentId)" +
        "VALUES (?, ?, ?, ?, ?)"
        );

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

  public User get(int id) {
    Connection conn = null;
    try {
      conn = connection.getConnection();
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Users WHERE Id = ?");
      pstmt.setInt(1, id);

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

  public User get(String userName) {
    Connection conn = null;
    try {
      conn = connection.getConnection();
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Users WHERE UserName = ?");
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