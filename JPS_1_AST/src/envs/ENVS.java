package envs;

import java.util.ArrayList;

import qres.collection.BagResult;

import edu.pjwstk.jps.datastore.ISBAStore;
import edu.pjwstk.jps.datastore.OID;
import edu.pjwstk.jps.interpreter.envs.IENVS;
import edu.pjwstk.jps.interpreter.envs.IENVSBinder;
import edu.pjwstk.jps.interpreter.envs.IENVSFrame;
import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IBagResult;
import edu.pjwstk.jps.result.ISingleResult;

public class ENVS implements IENVS {

	public ArrayList<IENVSFrame> stack = new ArrayList<IENVSFrame>();

	@Override
	public void init(OID rootOID, ISBAStore store) {
		// TODO Auto-generated method stub

	}

	@Override
	public IENVSFrame pop() {
		IENVSFrame frame = stack.get(stack.size() - 1);
		stack.remove(stack.size() - 1);
		return frame;
	}

	@Override
	public void push(IENVSFrame frame) {
		stack.add(frame);
	}

	@Override
	public IBagResult bind(String name) {
		BagResult bag = new BagResult();
		boolean found = false;

		for (int i = stack.size() - 1; i > 0; i--) {
			IENVSFrame frame = stack.get(i);
			for (IENVSBinder bg : frame.getElements()) {
				if (name == bg.getName()) {
					bag.add((ISingleResult) bg.getValue());
					found = true;
				}
			}
			if (found) {
				break;
			}
		}
		return bag;
	}

	@Override
	public IENVSFrame nested(IAbstractQueryResult result, ISBAStore store) {
		// TODO Auto-generated method stub
		return null;
	}

}
