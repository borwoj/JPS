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

	public String toString() {
		return "i" + id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyOID other = (MyOID) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
