package ast.binary;

import ast.Expression;
import edu.pjwstk.jps.ast.binary.IForAnyExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class ForAnyExpression extends BinaryExpression implements
		IForAnyExpression {

	public ForAnyExpression(Expression expLeft, Expression expRight) {
		super(expLeft, expRight);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitAnyExpression(this);

	}

}
