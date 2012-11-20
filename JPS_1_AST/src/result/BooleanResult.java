package result;

import edu.pjwstk.jps.result.IBooleanResult;

public class BooleanResult extends SimpleResult<Boolean> implements IBooleanResult {

	public BooleanResult(boolean value) {
		this.value = value;
	}
	
	public String toString(){
		return ""+value;
	}

}
