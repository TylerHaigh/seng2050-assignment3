package rgms.controller;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.servlet.ServletContext;

import rgms.mvc.*;
import rgms.infrastructure.*;
import rgms.model.*;
import rgms.datacontext.*;

import java.util.*;
import java.util.logging.*;
import java.io.*;

@MultipartConfig
@WebServlet(urlPatterns = { "/document/*", "/document" })
public class DocumentController extends Controller {
  private static final Logger logger = Logger.getLogger("rgms.controller.DocumentController");

  /**
   * Downloads a document from the server to the user's client machine
   * 
   * - Requires a documentId request parameter
   * 
   * @param req The HTTP Request
   * @param res The HTTP Response
   * @throws IOException Unable to download the document
   */
  public void downloadDocumentAction(HttpServletRequest req, HttpServletResponse res) throws IOException {
    if (AccountController.redirectIfNoCookie(req, res)) return;

    if (req.getMethod() == HttpMethod.Get) {
      
      //Get the document
      int documentId = Integer.parseInt(req.getParameter("documentId"));
      DocumentManager docMan = new DocumentManager();
      Document document = docMan.get(documentId);
      
      if (document != null) {
        //Get user id from cookie
        Cookie cookie = AccountController.getUserCookie(req);
        int userId = Integer.parseInt(cookie.getValue());

        //Create Access record.
        docMan.createAccessRecord(document, userId);

        //Download the file
        ServletContext context = getServletContext();
        String documentPath = String.format("%s/%s",
          context.getRealPath("/Uploads"), 
          document.getDocumentPath());

        //Create a stream
        File documentFile = new File(documentPath);
        FileInputStream stream = new FileInputStream(documentFile);

        String mimeType = context.getMimeType(documentPath);
        if (mimeType == null) {
          mimeType = "application/octet-stream";
        }

        res.setContentType(mimeType);
        res.setContentLength((int)documentFile.length());

        //forces download of file
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", document.getDocumentName());
            res.setHeader(headerKey, headerValue);
    
            OutputStream os = res.getOutputStream();
    
            byte[] buffer = new byte[1024];
            int read = -1;
    
            while ((read = stream.read(buffer)) != -1) {
              os.write(buffer, 0, read);
            }
    
            stream.close();
            os.close();
            return;
      }
    }
    
    httpNotFound(req, res);
  } 

  /**
   * Displays a Document for a HTTP Get
   * 
   * - Requires a cookie for the session user
   * - Requires a documentId requestParameter for the GET
   * 
   * @param req The HTTP Request
   * @param res The HTTP Response
   */
  public void documentAction(HttpServletRequest req, HttpServletResponse res) {
    //Ensure there is a cookie for the session user
    if (AccountController.redirectIfNoCookie(req, res)) return;
    
    Map<String, Object> viewData = new HashMap<>();
    
    if (req.getMethod() == HttpMethod.Get) {
      int threadId = Integer.parseInt(req.getParameter("threadId"));

      //get discussion details
      DiscussionManager discusMan = new DiscussionManager();
      DiscussionThread thread = discusMan.getThread(threadId);
      viewData.put("thread", thread);

      //Load Document data into Map
      DocumentManager docMan = new DocumentManager();
      List<Document> documents = docMan.getDocumentsForThread(threadId);      

      viewData.put("accessRecords", docMan.getAccessRecords(documents.get(0).getId()));
      
      if (!documents.isEmpty()) {
        viewData.put("title", documents.get(0).getDocumentName());
        viewData.put("documents", documents);

        view(req, res, "/views/document/Document.jsp", viewData);
        return;
      }       
    }
    httpNotFound(req, res);
  }

  public void uploadDocumentAction(HttpServletRequest req, HttpServletResponse res) {
    if (AccountController.redirectIfNoCookie(req, res)) return;

    Map<String, Object> viewData = new HashMap<>();
    if (req.getMethod() == HttpMethod.Post) {
      //get parameters
      int threadId = Integer.parseInt(req.getParameter("threadId"));
      int groupId = Integer.parseInt(req.getParameter("groupId"));
      int versionNumber = Integer.parseInt(req.getParameter("versionNumber"));

      try {
        Part documentPart = req.getPart("document");

        //if we have a document to upload
        if (documentPart.getSize() > 0) {
          String uuid = saveDocument(this.getServletContext(), documentPart);
          Document doc = new Document();
          doc.setDocumentName(getFileName(documentPart));
          doc.setDocumentPath(uuid);
          doc.setVersionNumber(versionNumber);
          doc.setThreadId(threadId);
          doc.setGroupId(groupId);

          DocumentManager docMan = new DocumentManager();
          docMan.createDocument(doc);
          
          //Get uploading User
          HttpSession session = req.getSession();
          Session userSession = (Session) session.getAttribute("userSession");
          User uploader = userSession.getUser();
          
          //Create a notification to all in the group
          NotificationManager notificationMan = new NotificationManager();
          GroupManager groupMan = new GroupManager();
          List<User> groupUsers = groupMan.getGroupUsers(groupId);
          
          for (User u : groupUsers) {
            Notification notification = new Notification(u.getId(), u,
                groupId, null,
                "User " + uploader.getFullName() + " has uploaded a document",
                "/document/document?documentId=" + doc.getId());
            
            notificationMan.createNotification(notification);
          }
        }
      }
      catch (Exception e) {
        logger.log(Level.SEVERE, "Document save error", e);
      }

      redirectToLocal(req, res, "/document/document/?threadId=" + threadId);
    }
    else 
      httpNotFound(req, res);
  }

  /**
   * Saves an uploaded document to the server hard disk
   * 
   * @param documentPart The request part for the document
   * @return The UUID string for the document name on disk
   */
  public static String saveDocument(ServletContext context, Part documentPart) {
      try {
        //get random uuid
        String id = UUID.randomUUID().toString();
  
        //save to disk
        String savePath = context.getRealPath("/Uploads") + "/" + id;
        documentPart.write(savePath);
  
        return id;
      }
      catch (Exception e) {
        logger.log(Level.SEVERE, "Error saving document", e);
        return null;
      } 
  }
}