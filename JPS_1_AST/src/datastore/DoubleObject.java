package datastore;

import edu.pjwstk.jps.datastore.IDoubleObject;

public class DoubleObject extends SimpleObject<Double> implements IDoubleObject {

	public DoubleObject(String name, double value) {
		super(name, value);
	}

}
