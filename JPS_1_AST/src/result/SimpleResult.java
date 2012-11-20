package result;

import edu.pjwstk.jps.result.ISimpleResult;

public abstract class SimpleResult<T> extends SingleResult implements ISimpleResult<T> {
	
	public T value;
	
	@Override
	public T getValue() {
		return value;
	}

}
