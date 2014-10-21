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
        "INSERT INTO Users (FirstName, LastName, UserName, Passphrase)" +
        "VALUES (?, ?, ?, ?)"
        );

      pstmt.setString(1, user.getFirstName());
      pstmt.setString(2, user.getLastName());
      pstmt.setString(3, user.getUserName());
      pstmt.setString(4, user.getPassword());

      pstmt.execute();
    }
    catch (Exception e) {
      logger.log(Level.SEVERE, "SQL Error", e);
    }
  }

  public User get(int Id) {
    // return (User)session.get(User.class, Id);
    return null;
  }

  public User get(String userName) {
    //todo;
    return null;
  }

  public boolean validate(String userName, String plainPassword) {
    // session.beginTransaction();

    // User user = this.get(userName);
    // return user.getPassword() == UserManager.hashPassword(plainPassword);
    return false;
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