package ast.binary;

import ast.Expression;
import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IBinaryExpression;

public abstract class BinaryExpression extends Expression implements
		IBinaryExpression {

	Expression expLeft, expRight;

	public BinaryExpression(Expression expLeft, Expression expRight) {
		this.expLeft = expLeft;
		this.expRight = expRight;
	}

	@Override
	public IExpression getLeftExpression() {
		return expLeft;
	}

	@Override
	public IExpression getRightExpression() {
		return expRight;
	}

}
