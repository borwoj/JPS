package ast.auxname;

import ast.Expression;
import edu.pjwstk.jps.ast.auxname.IAsExpression;

public class AsExpression extends AuxiliaryNameExpression implements IAsExpression {

	public AsExpression(Expression innerExp, String auxiliaryName) {
		super(innerExp, auxiliaryName);
	}

}
