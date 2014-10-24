package rgms.model;

import java.util.Date;

public class AccessRecord {

	//Private Instance Variables
	private int id;
	
	private int userId;
	private User user;
	
	private Date dateAccessed;
	
	private int documentId;
	private Document document;
	
	//Constructors
	
	public AccessRecord() {
		this.id = 1;
		this.userId = 1;
		this.user = new User();
		this.dateAccessed = new Date();
		this.documentId = 1;
		this.document = new Document();
	}

	public AccessRecord(int id, int userId, User user, Date dateAccessed,
			int documentId, Document document) {

		this.id = id;
		this.userId = userId;
		this.user = user;
		this.dateAccessed = dateAccessed;
		this.documentId = documentId;
		this.document = document;
	}

	//Getters
	
	public int getId() {
		return id;
	}

	public int getUserId() {
		return userId;
	}

	public User getUser() {
		return user;
	}

	public Date getDateAccessed() {
		return dateAccessed;
	}

	public int getDocumentId() {
		return documentId;
	}

	public Document getDocument() {
		return document;
	}

	//Setters
	
	public void setId(int id) {
		this.id = id;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setDateAccessed(Date dateAccessed) {
		this.dateAccessed = dateAccessed;
	}

	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
	
}
