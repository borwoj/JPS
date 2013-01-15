package ast.binary;

import ast.Expression;
import edu.pjwstk.jps.ast.binary.IEqualsExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class EqualsExpression extends BinaryExpression implements
		IEqualsExpression {

	public EqualsExpression(Expression expLeft, Expression expRight) {
		super(expLeft, expRight);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitEqualsExpression(this);

	}

}
