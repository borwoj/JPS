package ast.binary;

import ast.Expression;
import edu.pjwstk.jps.ast.binary.IGreaterThanExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class GreaterThanExpression extends BinaryExpression implements
		IGreaterThanExpression {

	public GreaterThanExpression(Expression expLeft, Expression expRight) {
		super(expLeft, expRight);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitGreaterThanExpression(this);

	}

}
