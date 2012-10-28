package qres.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import qres.AbstractQueryResult;

import edu.pjwstk.jps.result.IBagResult;
import edu.pjwstk.jps.result.ISingleResult;

public class BagResult extends CollectionResult implements IBagResult {

	public Collection<ISingleResult> list = new ArrayList<ISingleResult>();

	@Override
	public Collection<ISingleResult> getElements() {
		return list;
	}

	public void add(ISingleResult result) {
		list.add(result);
	}

	public String toString() {
		String str = "bag(";
		Iterator<ISingleResult> itr = list.iterator();
		int i = 0;
		while (itr.hasNext()) {
			ISingleResult element = itr.next();
			str = str + i + "=" + element;
			if (itr.hasNext()) {
				str += " , ";
			}
			i++;
		}
		str = str + ")";
		return str;
	}

}
