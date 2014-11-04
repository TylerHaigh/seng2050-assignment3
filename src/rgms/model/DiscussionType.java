package rgms.model;

import java.io.Serializable;

public enum DiscussionType implements Serializable {

	Undefined,
	Meeting,
	Document;
	
	public static DiscussionType fromInteger(int typeId) {
		switch (typeId) {
			case 1 : return Meeting;
			case 2 : return Document;
			default : return Undefined;
		}
	}
}
