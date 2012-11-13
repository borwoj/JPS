package envs;

import java.util.ArrayList;
import java.util.Collection;

import edu.pjwstk.jps.interpreter.envs.IENVSBinder;
import edu.pjwstk.jps.interpreter.envs.IENVSFrame;

public class ENVSFrame implements IENVSFrame {

	public ArrayList<IENVSBinder> elements = new ArrayList<IENVSBinder>();

	public void add(IENVSBinder element){
		elements.add(element);
	}
	
	@Override
	public Collection<IENVSBinder> getElements() {
		return elements;
	}

}
