package result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.ICollectionResult;
import edu.pjwstk.jps.result.ISingleResult;
import edu.pjwstk.jps.result.IStructResult;

public abstract class CollectionResult extends AbstractQueryResult implements ICollectionResult{

	protected ArrayList<ISingleResult> elements=new ArrayList<ISingleResult>();

	public CollectionResult(ISingleResult ... elements){
		this.elements.addAll(Arrays.asList(elements));
	}
	
	public CollectionResult(List<ISingleResult> elements){
		this.elements.addAll(elements);
	}
	
	public void add(ISingleResult element){
		elements.add(element);
	}
	
	public void addElements(IStructResult element){
		elements.addAll(element.elements());
	}
	
	public void add(Collection<ISingleResult> element){
		elements.addAll(element);
	}
	
	public void add(IAbstractQueryResult element){
		if(element instanceof ISingleResult){
			elements.add((ISingleResult)element);
		}else{
			elements.addAll(((CollectionResult)element).getElements());
		}
	}
		
	public int size(){
		return elements.size();
	}
	
	public Collection<ISingleResult> getElements(){
		return elements;
	}
		
}
