package qres.single;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import qres.AbstractQueryResult;

import edu.pjwstk.jps.result.ISingleResult;
import edu.pjwstk.jps.result.IStructResult;

public class StructResult extends SingleResult implements IStructResult {
	
	List<ISingleResult> list = new ArrayList<ISingleResult>();

	@Override
	public List<ISingleResult> elements() {
		return list;
	}

	public void add(ISingleResult element) { // TODO czy jest struktura
		list.add(element);
	}
	
	public String toString(){
		String str = "struct(";
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
