package ast.auxname;

import ast.Expression;
import edu.pjwstk.jps.ast.auxname.IGroupAsExpression;

public class GroupAsExpression extends AuxiliaryNameExpression implements IGroupAsExpression {

	public GroupAsExpression(Expression innerExp, String auxiliaryName) {
		super(innerExp, auxiliaryName);
	}

}
