package rgms.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Document implements Serializable {
	//Private Instance Variables
	private int id;
	private String documentPath; //Root Folder
	private String documentName;
	private int versionNumber;
	
	private int threadId;
	private int groupId;
	private DiscussionThread thread;
	
	//Constructors
	
	public Document() {
		this.id = 1;
		this.documentPath = "";
		this.documentName = "";
		this.versionNumber = 1;
		this.threadId = 1;
		this.thread = new DiscussionThread();
		this.groupId = 0;
	}
	
	public Document(int id, String documentPath, String documentName,
			int versionNumber, int threadId, DiscussionThread thread) {
		
		this.id = id;
		this.documentPath = documentPath;
		this.documentName = documentName;
		this.versionNumber = versionNumber;
		this.threadId = threadId;
		this.thread = thread;
	}

	//Getters
	
	public int getId() {
		return id;
	}

	public String getDocumentPath() {
		return documentPath;
	}

	public String getDocumentName() {
		return documentName;
	}

	public int getVersionNumber() {
		return versionNumber;
	}

	public int getThreadId() {
		return threadId;
	}

	public DiscussionThread getThread() {
		return thread;
	}
	
	public int getGroupId(){
		return groupId;
	}

	//Setters
	
	public void setId(int id) {
		this.id = id;
	}

	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}

	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}

	public void setThread(DiscussionThread thread) {
		this.thread = thread;
	}
	
	public void setGroupId(int groupId){
		this.groupId = groupId;
	}
	
	public static Document fromResultSet(ResultSet rs) {
		Document document = null;
		
		try {

			if (rs.next()) {
				document = new Document();
				document.setId(rs.getInt("Id"));
				document.setDocumentPath(rs.getString("DocumentPath"));
				document.setDocumentName(rs.getString("DocumentName"));
				document.setVersionNumber(rs.getInt("VersionNumber"));
				document.setGroupId(rs.getInt("GroupId"));
				
				Logger.getLogger("rgms.model.Document").info(
					String.format("Loaded Document: %d, %s, %s, %d, %d",
							document.getId(), document.getDocumentPath(), document.getDocumentName(),
							document.getVersionNumber(), document.getGroupId())
				);
			}
				
		} catch (SQLException e) {
			Logger.getLogger("rgms.model.Meeting").log(Level.SEVERE, "SQL Error", e);
		}
		
		return document;
	}
	
}
