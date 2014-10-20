package rgms.datacontext;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Criterion;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import rgms.model.User;

public class UserManager extends DataManager {
  public void createUser(User user, String plainPassword) {
    session.beginTransaction();

    String hashedPass = UserManager.hashPassword(plainPassword);
    user.setPassword(hashedPass);
    session.save(user);

    session.getTransaction().commit();
  }

  public User get(int Id) {
    return (User)session.get(User.class, Id);
  }

  public User get(String userName) {
    return (User)session.createCriteria(User.class).
       add(Restrictions.eq("userName", userName) ).
       uniqueResult();
  }

  public boolean validate(String userName, String plainPassword) {
    session.beginTransaction();

    User user = this.get(userName);
    return user.getPassword() == UserManager.hashPassword(plainPassword);
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