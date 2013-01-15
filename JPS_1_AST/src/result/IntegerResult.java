package result;

import edu.pjwstk.jps.result.IDoubleResult;
import edu.pjwstk.jps.result.IIntegerResult;

public class IntegerResult extends SimpleResult<Integer> implements
		IIntegerResult {

	public IntegerResult(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IIntegerResult || obj instanceof IDoubleResult)
			if (((Number) value).equals((Number) (((SimpleResult) obj).getValue())))
				return true;
		return false;
	}

	public String toString() {
		return "" + value;
	}

}
