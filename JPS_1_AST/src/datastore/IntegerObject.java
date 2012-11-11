package datastore;

import edu.pjwstk.jps.datastore.IIntegerObject;

public class IntegerObject extends SimpleObject<Integer> implements
		IIntegerObject {

	public IntegerObject(String name, int value) {
		super(name, value);
	}

}
