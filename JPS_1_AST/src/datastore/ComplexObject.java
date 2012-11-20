package datastore;

import java.util.ArrayList;
import java.util.List;

import edu.pjwstk.jps.datastore.IComplexObject;
import edu.pjwstk.jps.datastore.OID;

public class ComplexObject extends SBAObject implements IComplexObject {

	List<OID> childOIDs = new ArrayList<OID>();
	//public static ArrayList<ComplexObject> allComplexObjects = new ArrayList<ComplexObject>();

	public ComplexObject(String name) {
		super(name);
		//allComplexObjects.add(this);
		//SBAStore.allObjects.add(this);
	}

	@Override
	public List<OID> getChildOIDs() {
		return childOIDs;
	}

	public String toString() {

		String str = "<i" + getOID().id + ", " + getName() + ", {";

		int i = 0;
		for (OID oid : childOIDs) {
			if(i != 0){
				str += ", ";
			}
			str += oid;
			i++;
		}
		str += "}>";
		return str;
	}
}
