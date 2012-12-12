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

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ReferenceResult
				&& ((ReferenceResult) obj).getOIDValue() == getOIDValue())
			return true;
		return false;
	}

	public String toString() {
		return "" + value;
	}

}
