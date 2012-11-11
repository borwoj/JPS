package datastore;

import edu.pjwstk.jps.datastore.ISBAObject;
import edu.pjwstk.jps.datastore.OID;

public class SBAObject implements ISBAObject {

	public MyOID OID;
	public String name;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public OID getOID() {
		return OID;
	}

}
