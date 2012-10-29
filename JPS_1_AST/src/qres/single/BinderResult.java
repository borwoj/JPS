package qres.single;

import qres.AbstractQueryResult;
import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IBinderResult;

public class BinderResult extends SingleResult implements IBinderResult {

	public String name;
	public AbstractQueryResult value;

	public BinderResult(String name, AbstractQueryResult value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IAbstractQueryResult getValue() {
		return value;
	}

	public String toString() {
		return "binder(name = " + name + " , value = " + value+")";
	}

}
