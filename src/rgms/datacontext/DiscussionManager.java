package rgms.datacontext;

import rgms.model.DiscussionThread;
import rgms.model.DiscussionPost;
import java.util.logging.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class DiscussionManager extends DataManager {
  private final Logger logger = Logger.getLogger("rgms.datacontext.DiscussionManager");

  public void createDiscussion(DiscussionThread discussion) {
    try {
      Connection conn = connection.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(
        "INSERT INTO DiscussionThreads (GroupId, ThreadName)" +
        "VALUES (?, ?)");

      pstmt.setInt(1, discussion.getGroupId());
      pstmt.setString(2, discussion.getThreadName());
      pstmt.execute();
    }
    catch (Exception e) {
       logger.log(Level.SEVERE, "SQL Error", e);
    }
  }

  public void createPost(DiscussionPost post) {
    try {
      Connection conn = connection.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(
        "INSERT INTO DiscussionPosts (ThreadId, UserId, Message)" +
        "VALUES (?, ?, ?)");

      pstmt.setInt(1, post.getThreadId());
      pstmt.setInt(2, post.getUserId());
      pstmt.setString(3, post.getMessage());
      pstmt.execute();
    }
    catch (Exception e) {
      logger.log(Level.SEVERE, "SQL Error", e);
    }
  }

  public List<DiscussionThread> getThreads(int groupId) {
    ArrayList<DiscussionThread> threads = new ArrayList<>();

    try {
      Connection conn = connection.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(
        "SELECT * FROM DiscussionThreads WHERE GroupId = ?");

      pstmt.setInt(1, groupId);
      ResultSet rs = pstmt.executeQuery();

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

  public DiscussionThread getThread(int threadId) {
    DiscussionThread thread = null;

    try {
      Connection conn = connection.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(
        "SELECT * FROM DiscussionThreads WHERE ThreadId = ?");

      pstmt.setInt(1, threadId);
      ResultSet rs = pstmt.executeQuery();
      thread = DiscussionThread.fromResultSet(rs);
    }
    catch (Exception e) {
      logger.log(Level.SEVERE, "SQL Error", e);
    }

    return thread;
  }

  public List<DiscussionPost> getPosts(int threadId) {
    ArrayList<DiscussionPost> posts = new ArrayList<>();

    try {
      Connection conn = connection.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(
        "SELECT * FROM DiscussionPosts WHERE ThreadId = ?");

      pstmt.setInt(1, threadId);
      ResultSet rs = pstmt.executeQuery();
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