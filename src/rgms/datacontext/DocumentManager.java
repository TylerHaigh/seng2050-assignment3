package rgms.datacontext;

import java.util.List;
import java.util.LinkedList;
import java.util.logging.*;
import rgms.model.Document;
import java.sql.*;

public class DocumentManager extends DataManager {
  private static final Logger logger = Logger.getLogger("rgms.datacontext.DocumentManager");

  public void createDocument(Document document) {
    try {
      Connection conn = connection.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(
        "INSERT INTO Documents (DocumentName, DocumentPath, VersionNumber, ThreadId, GroupId)" +
        "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

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

   public List<Document> getGroupDocuments(String groupId){
     List<Document> groupDocuments = new LinkedList<Document>();
     Connection conn = null;
     int groupNum = Integer.parseInt(groupId);
     try {
       conn = connection.getConnection();
       PreparedStatement pstmt = conn.prepareStatement(
           "SELECT * FROM Documents " +
           "WHERE GroupId = ?");
      
       pstmt.setInt(1, groupNum);
       
       ResultSet rs = pstmt.executeQuery();
       
       if (rs.isBeforeFirst()) {
         while (!rs.isAfterLast()) {
           //This may throw null pointer exception if there are no documents
           //if (rs.next()) {
            Document d = Document.fromResultSet(rs);
            if(d != null){
              groupDocuments.add(d);
            }            
          // }        
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

    public List<Document> getDocumentsForThread(int threadId) {
     List<Document> groupDocuments = new LinkedList<Document>();
     Connection conn = null;
     try {
       conn = connection.getConnection();
       PreparedStatement pstmt = conn.prepareStatement(
           "SELECT * FROM Documents " +
           "WHERE ThreadId = ? ORDER BY VersionNumber DESC");
      
       pstmt.setInt(1, threadId);
       
       ResultSet rs = pstmt.executeQuery();
       
       if (rs.isBeforeFirst()) {
         while (!rs.isAfterLast()) {
           //This may throw null pointer exception if there are no documents
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
   
   public Document getDocument(int documentId){
     Document aDocument = new Document();
     Connection conn = null;
     try {
       conn = connection.getConnection();
       PreparedStatement pstmt = conn.prepareStatement(
           "SELECT * FROM documents " +
           "WHERE id = ?");
      
       pstmt.setInt(1, documentId);
       
       ResultSet rs = pstmt.executeQuery();
       
       if (rs.isBeforeFirst()) {
         while (!rs.isAfterLast()) {
           //This may throw null pointer exception if there are no documents
           //if (rs.next()) {
            aDocument = Document.fromResultSet(rs);
            return aDocument;
          // }        
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
     
     return aDocument;
  }
}