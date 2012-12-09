package ast.binary;

import ast.Expression;
import edu.pjwstk.jps.ast.binary.IGreaterOrEqualThanExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class GreaterOrEqualThanExpression extends BinaryExpression implements
		IGreaterOrEqualThanExpression {

	public GreaterOrEqualThanExpression(Expression expLeft, Expression expRight) {
		super(expLeft, expRight);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitGreaterOrEqualThanExpression(this);

	}

}
