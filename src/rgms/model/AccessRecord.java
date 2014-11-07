package rgms.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.Serializable;

import rgms.datacontext.UserManager;

public class AccessRecord implements Serializable {

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

	public static AccessRecord fromResultSet(ResultSet rs) {
		AccessRecord accessRecord = null;
		
		try {

			if (rs.next()) {
				//Get the date the document was accessed
				Timestamp accessedTimeStamp = rs.getTimestamp("DateAccessed");
				Date accessedDate = new Date(accessedTimeStamp.getTime());
				int userId = rs.getInt("userId");
				UserManager um = new UserManager();
				User user = um.get(userId);
				
				accessRecord = new AccessRecord();
				accessRecord.setId(rs.getInt("Id"));
				accessRecord.setUserId(userId);
				accessRecord.setUser(user);
				accessRecord.setDateAccessed(accessedDate);
				accessRecord.setDocumentId(rs.getInt("DocumentId"));
				
				Logger.getLogger("rgms.model.Document").info(
					String.format("Loaded Access Record: %d, %d, %d",
							accessRecord.getId(), accessRecord.getUserId(), accessRecord.getDocumentId())
				);
			}
				
		} catch (SQLException e) {
			Logger.getLogger("rgms.model.Meeting").log(Level.SEVERE, "SQL Error", e);
		}
		
		return accessRecord;
	}
	
}
