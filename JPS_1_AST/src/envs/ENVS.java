package envs;

import edu.pjwstk.jps.datastore.ISBAStore;
import edu.pjwstk.jps.datastore.OID;
import edu.pjwstk.jps.interpreter.envs.IENVS;
import edu.pjwstk.jps.interpreter.envs.IENVSFrame;
import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IBagResult;

public class ENVS implements IENVS {

	@Override
	public void init(OID rootOID, ISBAStore store) {
		// TODO Auto-generated method stub

	}

	@Override
	public IENVSFrame pop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void push(IENVSFrame frame) {
		// TODO Auto-generated method stub

	}

	@Override
	public IBagResult bind(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IENVSFrame nested(IAbstractQueryResult result, ISBAStore store) {
		// TODO Auto-generated method stub
		return null;
	}

}
