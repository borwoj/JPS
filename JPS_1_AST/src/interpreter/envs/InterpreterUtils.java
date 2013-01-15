package interpreter.envs;

import result.BagResult;
import result.BooleanResult;
import result.CollectionResult;
import result.DoubleResult;
import result.IntegerResult;
import result.ReferenceResult;
import result.SingleResult;
import result.StringResult;
import datastore.BooleanObject;
import datastore.StringObject;
import edu.pjwstk.jps.datastore.IBooleanObject;
import edu.pjwstk.jps.datastore.IComplexObject;
import edu.pjwstk.jps.datastore.IDoubleObject;
import edu.pjwstk.jps.datastore.IIntegerObject;
import edu.pjwstk.jps.datastore.ISBAObject;
import edu.pjwstk.jps.datastore.ISBAStore;
import edu.pjwstk.jps.datastore.ISimpleObject;
import edu.pjwstk.jps.datastore.IStringObject;
import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IBagResult;
import edu.pjwstk.jps.result.IBinderResult;
import edu.pjwstk.jps.result.IReferenceResult;
import edu.pjwstk.jps.result.ISingleResult;
import edu.pjwstk.jps.result.IStructResult;

public class InterpreterUtils {
	public static IAbstractQueryResult toSingleResult(IAbstractQueryResult res) {
		if (res instanceof IBagResult) {
			IBagResult leftBag = ((IBagResult) res);
			if (leftBag.getElements().size() == 1) {
				res = leftBag.getElements().iterator().next();
			} else {
				throw new RuntimeException(
						"rezultat nie moze byc bagiem wieloelementowym");
			}
		}
		if (res instanceof IStructResult) {
			IStructResult leftStruct = ((IStructResult) res);
			if (leftStruct.elements().size() == 1) {
				res = leftStruct.elements().iterator().next();
			} else {
				throw new RuntimeException(
						"rezultat nie moze byc struktura wieloelementowa");
			}
		}
		return res;
	}

	public static IAbstractQueryResult deref(IAbstractQueryResult res,
			ISBAStore store) {
		if (res instanceof IReferenceResult) {
			IReferenceResult ref = (IReferenceResult) res;
			ISBAObject obj = store.retrieve(ref.getOIDValue());
			if (obj instanceof ISimpleObject) {
				if (obj instanceof IIntegerObject) {
					IIntegerObject iobj = (IIntegerObject) obj;
					res = new IntegerResult(iobj.getValue());
				} else if (obj instanceof IDoubleObject) {
					IDoubleObject iobj = (IDoubleObject) obj;
					res = new DoubleResult(iobj.getValue());
				} else if (obj instanceof BooleanObject) {
					IBooleanObject iobj = (IBooleanObject) obj;
					res = new BooleanResult(iobj.getValue());
				} else if (obj instanceof StringObject) {
					IStringObject iobj = (IStringObject) obj;
					res = new StringResult(iobj.getValue());
				}

			} else if (obj instanceof IComplexObject) {
				return new ReferenceResult(((IComplexObject) obj).getOID());
			} else {
				throw new RuntimeException("excepted single object got: "
						+ obj.getClass());
			}
		} else if (res instanceof IBinderResult) {
			IBinderResult binder = (IBinderResult) res;
			res = binder.getValue();
		}
		return res;
	}

	public static IBagResult toBag(IAbstractQueryResult res) {
		if (res instanceof IBagResult) {
			return (IBagResult) res;
		} else if (res instanceof ISingleResult) {
			IBagResult bag = new BagResult();
			bag.getElements().add((ISingleResult) res);
			return bag;
		} else {
			throw new RuntimeException("");
		}

	}
}
