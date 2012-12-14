package result;

import edu.pjwstk.jps.result.IDoubleResult;
import edu.pjwstk.jps.result.IIntegerResult;

public class DoubleResult extends SimpleResult<Double> implements IDoubleResult {

	public DoubleResult(double value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IDoubleResult || obj instanceof IIntegerResult)
			if ((Number) value == (Number) (((SimpleResult) obj).getValue()))
				return true;
		return false;
	}

	public String toString() {
		return "" + value;
	}

}
