package result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IBagResult;
import edu.pjwstk.jps.result.ISingleResult;

public class BagResult extends CollectionResult implements IBagResult {

	public Collection<ISingleResult> list = new ArrayList<ISingleResult>();

	@Override
	public Collection<ISingleResult> getElements() {
		return list;
	}

	public void add(IAbstractQueryResult res) {
		if (res instanceof ISingleResult) {
			add((ISingleResult) res);
		} else if (res instanceof IBagResult) {
			add((IBagResult) res);
		}
	}

	public void add(ISingleResult result) {
		list.add(result);
	}

	public void add(IBagResult bagRes) {
		list.addAll(bagRes.getElements());
	}

	public String toString() {
		String str = "bag(";
		Iterator<ISingleResult> itr = list.iterator();
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
