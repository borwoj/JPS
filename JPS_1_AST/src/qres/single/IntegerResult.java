package qres.single;

import edu.pjwstk.jps.result.IIntegerResult;

public class IntegerResult extends SimpleResult<Integer> implements
		IIntegerResult {

	public Integer value;

	public IntegerResult(Integer value) {
		this.value = value;
	}
	
	public Integer getValue(){
		return value;
	}

	public String toString() {
		return "" + value;
	}

}
