package datastore;

import java.util.ArrayList;

import edu.pjwstk.jps.datastore.OID;

public class MyOID implements OID {

	public static ArrayList<Long> OIDs = new ArrayList<Long>();
	public long id;

	public MyOID(long id) {
		if (!OIDs.contains(id))
			this.id = id;
	}

}
