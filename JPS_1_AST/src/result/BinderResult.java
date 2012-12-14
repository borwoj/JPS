package result;

import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IBinderResult;

public class BinderResult extends SingleResult implements IBinderResult {

	public String name;
	public IAbstractQueryResult value;

	public BinderResult(String name, IAbstractQueryResult innerRes) {
		this.name = name;
		this.value = innerRes;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IAbstractQueryResult getValue() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IBinderResult
				&& ((IBinderResult) obj).getValue().equals(getValue())) 
			return true;
		return false;
	}

	public String toString() {
		return "binder(name = " + name + " , value = " + value + ")";
	}

}
