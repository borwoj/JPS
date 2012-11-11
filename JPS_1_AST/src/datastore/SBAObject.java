package datastore;

import edu.pjwstk.jps.datastore.ISBAObject;
import edu.pjwstk.jps.datastore.OID;

public abstract class SBAObject implements ISBAObject {

	public MyOID OID;
	public String name;

	public SBAObject(String name, MyOID OID) {
		this.name = name;
		this.OID = OID;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public OID getOID() {
		return OID;
	}

}
