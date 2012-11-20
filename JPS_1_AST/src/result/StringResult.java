package result;

import edu.pjwstk.jps.result.IStringResult;

public class StringResult extends SimpleResult<String> implements IStringResult {

	public StringResult(String value) {
		this.value = value;
	}
	
	public String toString(){
		return ""+value;
	}

}
