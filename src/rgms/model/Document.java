package rgms.model;

import java.io.Serializable;

public class Document implements Serializable {

	//Private Instance Variables
	private int id;
	private String documentPath; //Root Folder
	private String documentName;
	private int versionNumber;
	
	private int threadId;
	private DiscussionThread thread;
	
	//Constructors
	
	public Document() {
		this.id = 1;
		this.documentPath = "";
		this.documentName = "";
		this.versionNumber = 1;
		this.threadId = 1;
		this.thread = new DiscussionThread();
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
	
}
