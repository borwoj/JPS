package datastore;

import java.util.ArrayList;

import edu.pjwstk.jps.datastore.OID;

public class MyOID implements OID {

	public static ArrayList<Long> OIDs = new ArrayList<Long>();
	public long id;
	public static long idCounter = -1;

	public static MyOID createOID() {
		idCounter += 1;
		if (!OIDs.contains(idCounter))
			return new MyOID(idCounter);
		else {
			System.out.println("OID ju¿ istnieje!");
			return null;
		}
	}

	private MyOID(long OID) {
		this.id = OID;
	}
	
	public String toString(){
		return "i"+id;	
	}

}
