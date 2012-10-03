package ast.binary;

import ast.Expression;
import edu.pjwstk.jps.ast.binary.ICommaExpression;

public class CommaExpression extends BinaryExpression implements
		ICommaExpression {

	public CommaExpression(Expression expLeft, Expression expRight) {
		super(expLeft, expRight);
		// TODO Auto-generated constructor stub
	}

}
