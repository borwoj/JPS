package result;

import edu.pjwstk.jps.datastore.OID;
import edu.pjwstk.jps.result.IReferenceResult;

public class ReferenceResult extends SingleResult implements IReferenceResult {

	public OID value;

	public ReferenceResult(OID value) {
		this.value = value;
	}

	@Override
	public OID getOIDValue() {
		return value;
	}

	public String toString() {
		return "" + value;
	}

}
