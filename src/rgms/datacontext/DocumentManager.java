package rgms.datacontext;

import java.util.List;
import java.util.LinkedList;
import java.util.logging.*;
import rgms.model.Document;
import java.sql.*;

/**
 * Provides the functionality to communicate with the database and perform queries
 * pertaining to Documents
 * 
 * @author Tyler Haigh - C3182929
 * @author Simon Hartcher - C3185790
 * @author Josh Crompton - C3165877
 *
 */
public class DocumentManager extends DataManager {
  private static final Logger logger = Logger.getLogger("rgms.datacontext.DocumentManager");

  /**
   * Creates a Document in the database
   * @param document The Document to insert
   */
  public void createDocument(Document document) {
    try {
      Connection conn = connection.getConnection();
      
      //Create a prepared statement
      PreparedStatement pstmt = conn.prepareStatement(
        "INSERT INTO Documents (DocumentName, DocumentPath, VersionNumber, ThreadId, GroupId)" +
        "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

      //Set the required parameters and execute
      pstmt.setString(1, document.getDocumentName());
      pstmt.setString(2, document.getDocumentPath());
      pstmt.setInt(3, document.getVersionNumber());
      pstmt.setInt(4, document.getThreadId());
      pstmt.setInt(5, document.getGroupId());
      pstmt.executeUpdate();

      //get the generated id
      ResultSet rs = pstmt.getGeneratedKeys();
      if (rs.next())
        document.setId(rs.getInt(1));
    }
    catch (Exception e) {
      logger.log(Level.SEVERE, "SQL Error", e);
    }
  }

  /**
   * Retrieves a List of Documents belonging to a group
   * @param groupId The group's Id
   * @return A List of Documents belonging to the Group
   */
   public List<Document> getGroupDocuments(String groupId){
     List<Document> groupDocuments = new LinkedList<Document>();
     Connection conn = null;
     int groupNum = Integer.parseInt(groupId);
     
     try {
       conn = connection.getConnection();
       
       //Create a prepared statement
       PreparedStatement pstmt = conn.prepareStatement(
           "SELECT * FROM Documents " +
           "WHERE GroupId = ?");
      
       //Set the required parameters and execute
       pstmt.setInt(1, groupNum);
       ResultSet rs = pstmt.executeQuery();
       
       //Retrieve the result and add to the list
       if (rs.isBeforeFirst()) {
         while (!rs.isAfterLast()) {
            Document d = Document.fromResultSet(rs);
            if(d != null){
              groupDocuments.add(d);
            }            
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
     
     return groupDocuments;
   }

   /**
    * Retrieved a List of documents for a Thread
    * @param threadId The Id of the Thread
    * @return A List of Documents belonging to the Thread
    */
    public List<Document> getDocumentsForThread(int threadId) {
     List<Document> groupDocuments = new LinkedList<Document>();
     Connection conn = null;
     
     try {
       conn = connection.getConnection();
       
       //Create a Prepared Statement
       PreparedStatement pstmt = conn.prepareStatement(
           "SELECT * FROM Documents " +
           "WHERE ThreadId = ? ORDER BY VersionNumber DESC");
      
       //Set the required parameters and execute
       pstmt.setInt(1, threadId);
       ResultSet rs = pstmt.executeQuery();
       
       //Retrieve the results and store in the list
       if (rs.isBeforeFirst()) {
         while (!rs.isAfterLast()) {
            Document d = Document.fromResultSet(rs);
            if(d != null){
              groupDocuments.add(d);
            }            
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
     
     return groupDocuments;
    }
   
    /**
     * Retrieves the Document with the given Id
     * @param documentId The Id of the Document
     * @return The Document with the given Id
     */
   public Document get(int documentId){
     Document aDocument = new Document();
     Connection conn = null;
     
     try {
       conn = connection.getConnection();
       
       //Create a prepared statement
       PreparedStatement pstmt = conn.prepareStatement(
           "SELECT * FROM documents " +
           "WHERE id = ?");
      
       //Set the required parameters and execute
       pstmt.setInt(1, documentId);
       ResultSet rs = pstmt.executeQuery();

       //Retrieve the result
       if (rs.isBeforeFirst()) {
    	   aDocument = Document.fromResultSet(rs);
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
     
     return aDocument;
  }
   
}