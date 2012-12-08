package ast.binary;

import ast.Expression;
import edu.pjwstk.jps.ast.binary.IMinusExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class MinusExpression extends BinaryExpression implements
		IMinusExpression {

	public MinusExpression(Expression expLeft, Expression expRight) {
		super(expLeft, expRight);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitMinusExpression(this);

	}

}
