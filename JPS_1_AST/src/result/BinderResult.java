package result;

import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IBagResult;
import edu.pjwstk.jps.result.IBinderResult;
import edu.pjwstk.jps.result.ISingleResult;

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
		if (obj instanceof IBinderResult)
			if (getValue() instanceof IBagResult
					&& ((IBinderResult) obj).getValue() instanceof IBagResult) {
				// porownywanie bagow w celach testowych
				BagResult leftRes = (BagResult) getValue();
				BagResult rightRes = (BagResult) ((IBinderResult) obj)
						.getValue();
				return leftRes.equalsForJUnit(rightRes);
			} else if (((IBinderResult) obj).getValue().equals(getValue()))
				return true;
		return false;
	}

	public String toString() {
		return "binder(name = " + name + " , value = " + value + ")";
	}

	@Override
	public int compareTo(ISingleResult o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
