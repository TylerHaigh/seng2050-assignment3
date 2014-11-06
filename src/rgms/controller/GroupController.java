package rgms.controller;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.servlet.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.*;
import java.util.UUID;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.IOException;

import rgms.mvc.*;
import rgms.infrastructure.*;
import rgms.model.*;
import rgms.datacontext.*;

@MultipartConfig
@WebServlet(urlPatterns = { "/group/*", "/group" })
public class GroupController extends Controller {
	private static Logger logger = Logger.getLogger(GroupController.class.getName());

	public GroupController() { }
	
	public void researchgroupAction(HttpServletRequest req, HttpServletResponse res) {
	    Map<String, Object> viewData = new HashMap<String, Object>();
	    viewData.put("title", "Research Group");
	    
	    if (req.getMethod() == HttpMethod.Get) {
			//Load group data into Map
	    	GroupManager gm = new GroupManager();
		    int groupId = Integer.parseInt(req.getParameter("groupId"));
		    Group group = gm.get(groupId);		    
		    
		    if (group != null) {
		    	viewData.put("groupId", groupId);

		    	//Load group members into Map
			    viewData.put("groupName", group.getGroupName());			    

			    List<String> groupMembers = gm.getGroupMembers(groupId);
			    viewData.put("groupMembers", groupMembers);

		    	//Load meetings into map
			    MeetingManager meetMan = new MeetingManager();
			    List<Meeting> groupMeetings = meetMan.getGroupMeetings(groupId);
			    viewData.put("groupMeetings", groupMeetings);

			    //Load Document Data into Map
			    List<Document> groupDocuments = gm.getGroupDocuments(groupId);
			    viewData.put("groupDocuments", groupDocuments);

			    //Load discussion threads
			    DiscussionManager dm = new DiscussionManager();
			    viewData.put("groupDiscussions", dm.getThreads(groupId));

			    //Check if the user is a member
			    boolean isMember = false;
			    HttpSession session = req.getSession();
		    	Session userSession = (Session) session.getAttribute("userSession");
		    	User user = userSession.getUser();
		    	
		    	for (Group g : user.getGroups()) {
		    		if (g.getId() == group.getId()) {
		    			isMember = true;
		    			break;
		    		}
		    	}
		    	
		    	viewData.put("notMember", !isMember);
			    
			    //View group page.
		    	view(req, res, "/views/group/ResearchGroup.jsp", viewData);

		    } else {
		    	httpNotFound(req, res);
		    }
		    
		} else if (req.getMethod() == HttpMethod.Post) {
			//Create Group
			
			//Get data from parameters
			String groupName = req.getParameter("groupName");
			String description = req.getParameter("description");
			int adminId = Integer.parseInt(req.getParameter("createdByUserId"));
			
			//Create the Group
			GroupManager groupMan = new GroupManager();
			Group group = new Group();
			group.setGroupName(groupName);
			group.setDescription(description);
			
			//Create the mapping
			groupMan.createGroup(group);
			int groupId = groupMan.getIdFor(group);
			groupMan.createMapping(groupId, adminId);
			
			group.setId(groupId);
			
			//Update the User Session to show new meeting
    	HttpSession session = req.getSession();
    	Session userSession = (Session) session.getAttribute("userSession");
    	User admin = userSession.getUser();
    	admin.getGroups().add(group);
			
			//Show the Group Page
			viewData.put("groupName", group.getGroupName());
	    List<String> groupMembers = groupMan.getGroupMembers(groupId);
	    viewData.put("groupMembers", groupMembers);
		    
			view(req, res, "/views/group/ResearchGroup.jsp", viewData);
		}
	}
	
	public void meetingAction(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> viewData = new HashMap<String, Object>();
	    viewData.put("title", "Meeting");
	    
	    MeetingManager meetingMan = new MeetingManager();
	    GroupManager groupMan = new GroupManager();
	    
	    if (req.getMethod() == HttpMethod.Get) {
	    	int meetingId = Integer.parseInt(req.getParameter("meetingId"));
		    Meeting meeting = meetingMan.get(meetingId);
		    
		    if (meeting != null) {
			    
		    	List<User> meetingUsers = groupMan.getGroupUsers(meeting.getGroupId());
		    	
		    	viewData.put("meetingUsers", meetingUsers);
		    	viewData.put("meeting", meeting);
			    view(req, res, "/views/group/Meeting.jsp", viewData);
			    
		    } else {
		    	httpNotFound(req, res);
		    }
	    } else if (req.getMethod() == HttpMethod.Post) {
	    	
	    	//Get details from request
	    	String description = req.getParameter("description");
	    	int createdByUserId = Integer.parseInt(req.getParameter("createdByUserId"));
	    	Date dateCreated = new Date();
	    	
	    	String meetingDate = req.getParameter("datepicker");
	    	String meetingTime = req.getParameter("meetingTime");
	    	
	    	//Parse meeting date time details
	    	DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
	    	Date dateDue = new Date();
	    	try {
				dateDue = format.parse(meetingDate + " " + meetingTime);
			} catch (ParseException e) {
				//Unable to parse date
			}
	    	
	    	int groupId = Integer.parseInt(req.getParameter("groupId"));
	    	
	    	//Create a Meeting
	    	Meeting meeting = new Meeting();
	    	meeting.setDescription(description);
	    	meeting.setCreatedByUserId(createdByUserId);
	    	meeting.setDateCreated(dateCreated);
	    	meeting.setDateDue(dateDue);
	    	meeting.setGroupId(groupId);
	    	
	    	meetingMan.createMeeting(meeting);
	    	int meetingId = meetingMan.getIdFor(meeting);
	    	meeting.setId(meetingId);
	    	
	    	UserManager userMan = new UserManager();
	    	User createdByUser = userMan.get(createdByUserId);
	    	
	    	//Create a notification for all users in group
	    	NotificationManager notificationMan = new NotificationManager();
	    	List<User> users = groupMan.getGroupUsers(groupId);
	    	
	    	for (User u : users) {
	    		Notification notification = new Notification(u.getId(), u, groupId,
	    				null, "Meeting " + description + " was created by " + createdByUser.getFullName(),
	    				"/group/meeting?meetingId=" + meetingId);
	    		notificationMan.createNotification(notification);
	    	}
	    	
	    	//Update the User Session to show new meeting
	    	HttpSession session = req.getSession();
	    	Session userSession = (Session) session.getAttribute("userSession");
	    	User admin = userSession.getUser();
	    	admin.getMeetings().add(meeting);
	    	
	    	//Show meeting page
	    	viewData.put("meetingUsers", users);
	    	viewData.put("meeting", meeting);
		    view(req, res, "/views/group/Meeting.jsp", viewData);
	    	
	    }
	}
	
	public void documentAction(HttpServletRequest req, HttpServletResponse res) {
	    Map<String, Object> viewData = new HashMap<String, Object>();
	    viewData.put("title", "Document");
	    
	    if (req.getMethod() == HttpMethod.Get) {
			//Load Document data into Map
	    	GroupManager gm = new GroupManager();
		    int documentId = Integer.parseInt(req.getParameter("documentId"));
		    Document aDocument= gm.getDocument(documentId);		    
		    if (aDocument != null) {
		    	viewData.put("document", aDocument);
			    //View group page.
		    	view(req, res, "/views/group/Document.jsp", viewData);
		    } else {
		    	httpNotFound(req, res);
		    }
		    
		} else if (req.getMethod() == HttpMethod.Post) {
			//404
			httpNotFound(req, res);
		}
	}
	
	public void deletemeetingAction(HttpServletRequest req, HttpServletResponse res) {
		if (req.getMethod() == HttpMethod.Get) {
			
			int meetingId = Integer.parseInt(req.getParameter("meetingId"));
			MeetingManager meetingMan = new MeetingManager();
			Meeting meeting = meetingMan.get(meetingId);
			meetingMan.deleteMeeting(meetingId);
			
			//Update the User Session to remove meeting
	    	HttpSession session = req.getSession();
	    	Session userSession = (Session) session.getAttribute("userSession");
	    	List<Meeting> adminMeetings = userSession.getUser().getMeetings();

	    	for (int i = 0; i < adminMeetings.size(); i++) {
	    		Meeting m = adminMeetings.get(i);
	    		if (m.getId() == meeting.getId()) {
	    			adminMeetings.remove(i);
	    			break;
	    		}
	    	}
	    	
	    	redirectToLocal(req, res, "/home/dashboard");
			
		} else if (req.getMethod() == HttpMethod.Post) {
			httpNotFound(req, res);
		}
		
	}

	public void discussionAction(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> viewData = new HashMap<>();

		if (req.getMethod() == HttpMethod.Get) {
			int threadId = Integer.parseInt(req.getParameter("threadId"));

			DiscussionManager discussionManager = new DiscussionManager();
			DiscussionThread thread = discussionManager.getThread(threadId);
			thread.setPosts(discussionManager.getPosts(threadId));

			//get documents for the thread
			DocumentManager docMan = new DocumentManager();
			viewData.put("documents", docMan.getDocumentsForThread(threadId));

			viewData.put("thread", thread);
			viewData.put("title", "Discussion: " + thread.getThreadName());

			view(req, res, "/views/group/DiscussionThread.jsp", viewData);
		}
		else {
			httpNotFound(req, res);
		}
	}

	public void createDiscussionAction(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> viewData = new HashMap<>();

		if (req.getMethod() == HttpMethod.Get) {
			viewData.put("title", "Create Discussion");
			viewData.put("groupId", req.getParameter("groupId"));

			view(req, res, "/views/group/CreateDiscussion.jsp", viewData);
			return;
		}
		else if (req.getMethod() == HttpMethod.Post) {
			//save discussion
			DiscussionThread thread = new DiscussionThread();
			thread.setGroupId(Integer.parseInt(req.getParameter("groupId")));
			thread.setThreadName(req.getParameter("threadName"));

			DiscussionManager dm = new DiscussionManager();
			dm.createDiscussion(thread);

			try {
				Part documentPart = req.getPart("document");

				//if we have a document to upload
				if (documentPart.getSize() > 0) {
					String uuid = saveDocument(documentPart);
					Document doc = new Document();
					doc.setDocumentName(getFileName(documentPart));
					doc.setDocumentPath(uuid);
					doc.setVersionNumber(1);
					doc.setThreadId(thread.getId());
					doc.setGroupId(thread.getGroupId());

					DocumentManager docMan = new DocumentManager();
					docMan.createDocument(doc);
				}
			}
			catch (Exception e) {
				logger.log(Level.SEVERE, "Document save error", e);
			}

			redirectToLocal(req, res, "/group/discussion/?threadId=" + thread.getId());
			return;
		}
		httpNotFound(req, res);
	}

  private String getFileName(Part part) {
    for (String cd : part.getHeader("content-disposition").split(";")) {
      if (cd.trim().startsWith("filename")) {
        return cd.substring(cd.indexOf('=') + 1).trim()
            .replace("\"", "");
      }
    }
    return null;
  }

	private String saveDocument(Part documentPart) {
    try {
      //get random uuid
      String id = UUID.randomUUID().toString();

      //save to disk
      String savePath = getServletContext().getRealPath("/Uploads") + "/" + id;
      documentPart.write(savePath);

      return id;
    }
    catch (Exception e) {
      logger.log(Level.SEVERE, "Error saving document", e);
      return null;
    }	
	}

	public void createPostAction(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> viewData = new HashMap<>();

		if (req.getMethod() == HttpMethod.Post) {
			DiscussionManager dm = new DiscussionManager();

			HttpSession session = req.getSession();
			Session userSession = (Session)session.getAttribute("userSession");
			DiscussionPost post = new DiscussionPost();
			post.setUserId(userSession.getUserId());
			post.setMessage(req.getParameter("comment"));
			post.setThreadId(Integer.parseInt(req.getParameter("threadId")));
			dm.createPost(post);

			redirectToLocal(req, res, "/group/discussion/?threadId=" + req.getParameter("threadId"));
		}
		else {
			httpNotFound(req, res);
		}
	}

	public void inviteAction(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> viewData = new HashMap<String, Object>();
		
		int groupId = Integer.parseInt(req.getParameter("groupId"));
		
		try {
		
	    	HttpSession session = req.getSession();
	    	Session userSession = (Session) session.getAttribute("userSession");
	    	User user = userSession.getUser();
	    	
	    	GroupManager groupMan = new GroupManager();
	    	Group group = groupMan.get(groupId);
	    	User coordinator = groupMan.getCoordinator(groupId);
	    	
	    	NotificationManager notificationMan = new NotificationManager();
	    	Notification notification = new Notification(coordinator.getId(), coordinator,
	    			groupId, group, user.getFullName() + " wants to join your group " + group.getGroupName(),
	    			"/home/notifications?addUserId=" + user.getId() + "&groupId=" + group.getId());
	    	notificationMan.createNotification(notification);
	    	
	    	redirectToLocal(req, res, "/home/dashboard");
    	
		} catch (Exception e) {
			redirectToLocal(req, res, "/home/dashboard");
		}
    	
	}

	public void downloadDocumentAction(HttpServletRequest req, HttpServletResponse res) throws IOException {
		if (req.getMethod() == HttpMethod.Get) {
			int documentId = Integer.parseInt(req.getParameter("documentId"));
			DocumentManager docMan = new DocumentManager();
			Document document = docMan.get(documentId);
			if (document != null) {
				ServletContext context = getServletContext();
				String documentPath = String.format("%s/%s",
					context.getRealPath("/Uploads"), 
					document.getDocumentPath());

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
}
