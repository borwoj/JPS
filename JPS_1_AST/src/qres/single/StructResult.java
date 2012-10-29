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

	public void add(ISingleResult element) {
		list.add(element);
	}
	
	public String toString(){
		String str = "struct(";
		Iterator<ISingleResult> itr = list.iterator();
	    while (itr.hasNext()) {
	      ISingleResult element = itr.next();
	      str = str + element;
	    }		
	    str = str + ")";
		return str;
	}

}
