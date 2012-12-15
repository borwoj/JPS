package interpreter.envs;

import java.util.ArrayList;

import result.BagResult;
import result.BinderResult;
import result.BooleanResult;
import result.DoubleResult;
import result.IntegerResult;
import result.ReferenceResult;
import result.SimpleResult;
import result.StringResult;
import result.StructResult;
import datastore.ComplexObject;
import edu.pjwstk.jps.datastore.ISBAObject;
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

		ComplexObject root = (ComplexObject) store.retrieve(rootOID);
		ENVSFrame frame = new ENVSFrame();

		if (root != null) {
			for (OID oid : root.getChildOIDs()) {
				ISBAObject object = store.retrieve(oid);
				ENVSBinder binder = new ENVSBinder(object.getName(),
						new ReferenceResult(oid));
				frame.add(binder);
			}
			stack.add(frame);
		}

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

		for (int i = stack.size() - 1; i >= 0; i--) {
			IENVSFrame frame = stack.get(i);
			for (IENVSBinder bg : frame.getElements()) {
				if (name.equals(bg.getName())) {
					if (bg.getValue() instanceof ISingleResult) {
						bag.add((ISingleResult) bg.getValue());
					} else {
						BagResult br = (BagResult) bg.getValue();
						bag.add(br);
					}
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
	public ENVSFrame nested(IAbstractQueryResult result, ISBAStore store) {

		ENVSFrame frame = new ENVSFrame();

		if (result instanceof ReferenceResult) {
			ISBAObject sbao = store.retrieve(((ReferenceResult) result)
					.getOIDValue());

			if (sbao instanceof ComplexObject) {

				for (OID oid : ((ComplexObject) sbao).getChildOIDs()) {
					ISBAObject object = store.retrieve(oid);
					IENVSBinder binder = new ENVSBinder(object.getName(),
							new ReferenceResult(oid));
					frame.add(binder);
				}
			}

		} else if (result instanceof BinderResult) {
			ENVSBinder binder = new ENVSBinder(
					(((BinderResult) result).getName()),
					((BinderResult) result).getValue());
			frame.add(binder);
		} else if (result instanceof StructResult) {
			for (ISingleResult s : ((StructResult) result).elements()) {
				frame = nested(s, store);
			}
		} else if (result instanceof BooleanResult
				|| result instanceof DoubleResult
				|| result instanceof IntegerResult
				|| result instanceof StringResult) {
			// pusty zbior
		} else if (result instanceof SimpleResult) {
			// pusty zbior
		} else {
			// pusty zbior
		}

		return frame;

	}

	public String toString() {
		String str = "";
		int i = 0;
		for (IENVSFrame frame : stack) {
			if (i != 0) {
				str += ", ";
			}
			str += frame;
			i++;
		}
		return str;
	}

}
