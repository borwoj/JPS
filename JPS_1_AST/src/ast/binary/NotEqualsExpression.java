package ast.binary;

import ast.Expression;
import edu.pjwstk.jps.ast.binary.INotEqualsExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class NotEqualsExpression extends BinaryExpression implements
		INotEqualsExpression {

	public NotEqualsExpression(Expression expLeft, Expression expRight) {
		super(expLeft, expRight);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitNotEqualsExpression(this);

	}

}
