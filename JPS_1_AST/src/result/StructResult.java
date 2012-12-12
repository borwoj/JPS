package result;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pjwstk.jps.result.ISingleResult;
import edu.pjwstk.jps.result.IStructResult;

public class StructResult extends SingleResult implements IStructResult {

	List<ISingleResult> elements = new ArrayList<ISingleResult>();

	@Override
	public List<ISingleResult> elements() {
		return elements;
	}

	public void add(ISingleResult element) { // TODO czy jest struktura
		elements.add(element);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IStructResult) {
			IStructResult structRes = (IStructResult) obj;
			if (structRes.elements().containsAll(elements)
					&& structRes.elements().size() == elements.size())
				return true;

		}
		return false;
	}

	public String toString() {
		String str = "struct(";
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
