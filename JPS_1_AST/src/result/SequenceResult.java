package result;

import java.util.ArrayList;
import java.util.List;

import edu.pjwstk.jps.result.ISequenceResult;
import edu.pjwstk.jps.result.ISingleResult;

public class SequenceResult extends CollectionResult implements ISequenceResult {
	
	List<ISingleResult> list = new ArrayList<ISingleResult>();

	@Override
	public List<ISingleResult> getElements() {
		return list;
	}

}
