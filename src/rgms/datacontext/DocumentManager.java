package rgms.datacontext;

import java.util.List;
import java.util.LinkedList;
import java.util.logging.*;
import rgms.model.Document;
import java.sql.*;

public class DocumentManager extends DataManager {
  private static final Logger logger = Logger.getLogger("rgms.datacontext.DocumentManager");

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