package result;

import java.util.Iterator;
import java.util.List;

import edu.pjwstk.jps.result.ISequenceResult;
import edu.pjwstk.jps.result.ISingleResult;
import edu.pjwstk.jps.result.IStructResult;

public class SequenceResult extends CollectionResult implements ISequenceResult {

	// List<ISingleResult> list = new ArrayList<ISingleResult>();

	@Override
	public List<ISingleResult> getElements() {
		return elements;
	}

	public String toString() {
		String str = "sequence(";
		Iterator<ISingleResult> itr = elements.iterator();
		int i = 0;
		while (itr.hasNext()) {
			ISingleResult element = itr.next();
			str = str + i + " = " + element;
			if (itr.hasNext()) {
				str += " , ";
			}
			i++;
		}
		str = str + ")";
		return str;
	}

	/**
	 * Metoda tylko w celach testowych.
	 */
	public boolean equalsForJUnit(Object obj) {
		if (obj instanceof SequenceResult) {
			SequenceResult seqRes = (SequenceResult) obj;
			if (seqRes.getElements().containsAll(elements)
					&& seqRes.getElements().size() == elements.size())
				return true;

		}
		return false;
	}
}
