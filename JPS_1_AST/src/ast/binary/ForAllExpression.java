package ast.binary;

import ast.Expression;
import edu.pjwstk.jps.ast.binary.IForAllExpression;

public class ForAllExpression extends BinaryExpression implements IForAllExpression {

	public ForAllExpression(Expression expLeft, Expression expRight) {
		super(expLeft, expRight);
	}

}
