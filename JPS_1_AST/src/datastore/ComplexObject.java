package datastore;

import java.util.List;

import edu.pjwstk.jps.datastore.IComplexObject;
import edu.pjwstk.jps.datastore.OID;

public class ComplexObject extends SBAObject implements IComplexObject {

	public ComplexObject(String name, MyOID OID) {
		super(name, OID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<OID> getChildOIDs() {
		// TODO Auto-generated method stub
		return null;
	}

}
