package result;

import datastore.MyOID;
import edu.pjwstk.jps.datastore.OID;
import edu.pjwstk.jps.result.IReferenceResult;

public class ReferenceResult extends SingleResult implements IReferenceResult {

	public MyOID value;

	public ReferenceResult(OID value) {
		this.value = (MyOID) value;
	}

	@Override
	public MyOID getOIDValue() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		long a= value.id;
		long b= ((ReferenceResult) obj).getOIDValue().id;
		if (obj instanceof ReferenceResult
				&& ((ReferenceResult) obj).getOIDValue().equals(getOIDValue()))
			return true;
		return false;
	}

	public String toString() {
		return "" + value;
	}

}
