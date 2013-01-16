package result;

import edu.pjwstk.jps.result.IDoubleResult;
import edu.pjwstk.jps.result.IIntegerResult;
import edu.pjwstk.jps.result.ISingleResult;

public class DoubleResult extends SimpleResult<Double> implements IDoubleResult {

	public DoubleResult(double value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IDoubleResult || obj instanceof IIntegerResult)
			if (((Number) value).equals((Number) (((SimpleResult) obj)
					.getValue())))
				return true;
		return false;
	}

	public String toString() {
		return "" + value;
	}

	@Override
	public int compareTo(ISingleResult o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
