package result;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import edu.pjwstk.jps.result.IBagResult;
import edu.pjwstk.jps.result.ISingleResult;

public class BagResult extends CollectionResult implements IBagResult {
	public BagResult(ISingleResult... elements) {
		super(elements);
	}

	public BagResult(List<ISingleResult> elements) {
		super(elements);
	}

	@Override
	public Collection<ISingleResult> getElements() {
		return elements;
	}

	/**
	 * Metoda tylko w celach testowych.
	 */
	public boolean equalsForJUnit(Object obj) {
		if (obj instanceof BagResult) {
			BagResult bagRes = (BagResult) obj;
			if (bagRes.getElements().containsAll(elements)
					&& bagRes.getElements().size() == elements.size())
				return true;

		}
		return false;
	}

	public String toString() {
		String str = "bag(";
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

}
