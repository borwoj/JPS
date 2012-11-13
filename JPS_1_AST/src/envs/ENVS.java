package envs;

import java.util.ArrayList;

import datastore.DoubleObject;
import datastore.IntegerObject;
import datastore.SBAObject;

import qres.collection.BagResult;
import qres.collection.CollectionResult;
import qres.single.BinderResult;
import qres.single.BooleanResult;
import qres.single.DoubleResult;
import qres.single.IntegerResult;
import qres.single.ReferenceResult;
import qres.single.SimpleResult;
import qres.single.StringResult;
import qres.single.StructResult;

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

		ENVSFrame frame = new ENVSFrame();

		if (result instanceof CollectionResult) {
			return frame;
		} else if (result instanceof ReferenceResult) {
			return frame;
		} else if (result instanceof BinderResult) {
			ENVSBinder binder = new ENVSBinder((((BinderResult) result).getName()), ((BinderResult) result).getValue());
			frame.add(binder);
			return frame;
		} else if (result instanceof StructResult) {
			return frame;
		} else if (result instanceof BooleanResult
				|| result instanceof DoubleResult
				|| result instanceof IntegerResult
				|| result instanceof StringResult) {
			return frame; // pusty zbior
		} else if (result instanceof SimpleResult) {
			return frame; // pusty zbior
		} else {
			return frame; // pusty zbior
		}

	}

}
