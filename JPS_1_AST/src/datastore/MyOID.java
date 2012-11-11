package datastore;

import java.util.ArrayList;

import edu.pjwstk.jps.datastore.OID;

public class MyOID implements OID {

	public static ArrayList<Long> OIDs = new ArrayList<Long>();
	public long id;
	public static long idCounter = -1;

	public MyOID() {
		idCounter += 1;
		if (!OIDs.contains(idCounter))
			this.id = idCounter;
	}

}
