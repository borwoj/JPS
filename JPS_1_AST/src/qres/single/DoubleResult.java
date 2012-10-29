package qres.single;

import edu.pjwstk.jps.result.IDoubleResult;

public class DoubleResult extends SimpleResult<Double> implements IDoubleResult {

	public DoubleResult(double value) {
		this.value = value;
	}

	public String toString() {
		return "" + value;
	}

}
