package datastore;

import java.util.ArrayList;
import java.util.HashMap;

import edu.pjwstk.jps.datastore.ISimpleObject;
import edu.pjwstk.jps.datastore.OID;

public abstract class SimpleObject<T> extends SBAObject implements
		ISimpleObject<T> {
	public static ArrayList<SimpleObject> allSimpleObjects = new ArrayList<SimpleObject>();

	public T value;

	public SimpleObject(String name, T value) {
		super(name);
		this.value = value;
		allSimpleObjects.add(this);
	}

	@Override
	public T getValue() {
		return value;
	}

	public String toString() {

		String str = "<i" + getOID().id + ", " + getName() + ", " + getValue()
				+ ">";
		return str;
	}
}
