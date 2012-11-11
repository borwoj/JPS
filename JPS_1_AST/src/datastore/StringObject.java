package datastore;

import edu.pjwstk.jps.datastore.IStringObject;

public class StringObject extends SimpleObject<String> implements IStringObject {

	public StringObject(String name, String value) {
		super(name, value);
	}

}
