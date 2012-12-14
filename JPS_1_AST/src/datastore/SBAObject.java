package datastore;

import java.util.HashMap;

import edu.pjwstk.jps.datastore.ISBAObject;

public abstract class SBAObject implements ISBAObject {

	public MyOID OID;
	public String name;

	/**
	 * Metoda tylko w celach testowych.
	 */
	public static HashMap<String, MyOID> allObjectsForJUnit = new HashMap<String, MyOID>();

	public SBAObject(String name) {
		this.name = name;
		this.OID = MyOID.createOID();
		allObjectsForJUnit.put(name, this.OID);
		SBAStore.allObjectsMap.put(OID, this);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public MyOID getOID() {
		return OID;
	}

}
