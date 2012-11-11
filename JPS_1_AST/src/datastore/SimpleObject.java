package datastore;

import edu.pjwstk.jps.datastore.ISimpleObject;

public abstract class SimpleObject<T> extends SBAObject implements
		ISimpleObject<T> {

	public T value;

	public SimpleObject(String name, MyOID OID) {
		super(name, OID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public T getValue() {
		return value;
	}

}
