package rgms.datacontext;

import rgms.model.DiscussionThread;
import rgms.model.DiscussionPost;
import java.util.logging.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Provides the functionality to communicate with the database and perform queries
 * pertaining to Discussions
 * 
 * @author Tyler Haigh - C3182929
 * @author Simon Hartcher - C3185790
 * @author Josh Crompton - C3165877
 *
 */
public class DiscussionManager extends DataManager {
  private final Logger logger = Logger.getLogger("rgms.datacontext.DiscussionManager");

  /**
   * Creates a Discussion Thread in the database
   * @param discussion The Discussion to insert
   */
  public void createDiscussion(DiscussionThread discussion) {
    try {
      //Create a prepared statement
      PreparedStatement pstmt = conn.prepareStatement(
        "INSERT INTO DiscussionThreads (GroupId, ThreadName)" +
        "VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);

      //Set the required parameters and execute
      pstmt.setInt(1, discussion.getGroupId());
      pstmt.setString(2, discussion.getThreadName());
      pstmt.executeUpdate();

      //Get the generated id
      ResultSet rs = pstmt.getGeneratedKeys();
      if (rs.next())
        discussion.setId(rs.getInt(1));
    }
    catch (Exception e) {
       logger.log(Level.SEVERE, "SQL Error", e);
    }
  }

  /**
   * Creates a Discussion Post in the database
   * @param post The Discussion Post to insert
   */
  public void createPost(DiscussionPost post) {
    try {
      //Create a prepared statement
      PreparedStatement pstmt = conn.prepareStatement(
        "INSERT INTO DiscussionPosts (ThreadId, UserId, Message)" +
        "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

      //Set the required parameters and execute
      pstmt.setInt(1, post.getThreadId());
      pstmt.setInt(2, post.getUserId());
      pstmt.setString(3, post.getMessage());
      pstmt.executeUpdate();

      //get the generated id
      ResultSet rs = pstmt.getGeneratedKeys();
      if (rs.next())
        post.setId(rs.getInt(1));
    }
    catch (Exception e) {
      logger.log(Level.SEVERE, "SQL Error", e);
    }
  }

  /**
   * Retrieves all of the Discussion Threads associated with a Group Id
   * 
   * @param groupId The Group Id to get Threads for
   * @return A List of Discussion Threads that belong to the Group
   */
  public List<DiscussionThread> getThreads(int groupId) {
    ArrayList<DiscussionThread> threads = new ArrayList<>();

    try {
      //Create a prepared statement
      PreparedStatement pstmt = conn.prepareStatement(
        "SELECT * FROM DiscussionThreads WHERE GroupId = ?");

      //Set the required parameters and execute
      pstmt.setInt(1, groupId);
      ResultSet rs = pstmt.executeQuery();

      //Get the results and add to the list
      if (rs.isBeforeFirst()) {
        while (!rs.isAfterLast()) {
          DiscussionThread thread = DiscussionThread.fromResultSet(rs);
          if (thread != null) {
            threads.add(thread);
          }
        }
      }
    }
    catch (Exception e) {
      logger.log(Level.SEVERE, "SQL Error", e);
    }

    return threads;
  }

  /**
   * Retrieves a single thread based on its Id
   * @param threadId The Id of the Thread
   * @return The Thread with the given Id
   */
  public DiscussionThread getThread(int threadId) {
    DiscussionThread thread = null;

    try {
      //Create a prepared statement
      PreparedStatement pstmt = conn.prepareStatement(
        "SELECT * FROM DiscussionThreads WHERE Id = ?");

      //Set the required parameters and execute
      pstmt.setInt(1, threadId);
      ResultSet rs = pstmt.executeQuery();
      thread = DiscussionThread.fromResultSet(rs);
    }
    catch (Exception e) {
      logger.log(Level.SEVERE, "SQL Error", e);
    }

    return thread;
  }

  /**
   * Retrieves a List of Discussion Posts for a given Thread Id
   * @param threadId The Id of the Thread to query
   * @return A List of Discussion Posts for the Thread
   */
  public List<DiscussionPost> getPosts(int threadId) {
    ArrayList<DiscussionPost> posts = new ArrayList<>();

    try {
      //Create a prepared statement
      PreparedStatement pstmt = conn.prepareStatement(
        "SELECT * FROM DiscussionPosts WHERE ThreadId = ?");

      //Set the required parameters adn execute
      pstmt.setInt(1, threadId);
      ResultSet rs = pstmt.executeQuery();
      
      //Retrieve the results and add to the list
      if (rs.isBeforeFirst()) {
        while (!rs.isAfterLast()) {
          DiscussionPost post = DiscussionPost.fromResultSet(rs);

          if (post != null)
        	  posts.add(post);
        }
      }
    }
    catch (Exception e) {
      logger.log(Level.SEVERE, "SQL Error", e);
    }

    return posts;

  }
}