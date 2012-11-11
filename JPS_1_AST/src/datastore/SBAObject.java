package datastore;

import java.util.HashMap;

import edu.pjwstk.jps.datastore.ISBAObject;
import edu.pjwstk.jps.datastore.OID;

public abstract class SBAObject implements ISBAObject {

	public MyOID OID;
	public String name;
	public static HashMap<Long, SBAObject> allObjects = new HashMap<Long, SBAObject>();

	public SBAObject(String name) {
		this.name = name;
		this.OID = MyOID.createOID();
		allObjects.put(this.OID.id, this);
		
		SBAStore.allObjects.add(this);
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
