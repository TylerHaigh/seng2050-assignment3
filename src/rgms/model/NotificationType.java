package rgms.model;

public enum NotificationType {

	Undefined,
	RegisteringUser,
	Meeting,
	Document,
	DiscussionPost;
	
	public static NotificationType fromInteger(int typeId) {
		switch (typeId) {
			case 1 : return RegisteringUser;
			case 2 : return Meeting;
			case 3 : return Document;
			case 4 : return DiscussionPost;
			default : return Undefined;
		}
	}
	
}
