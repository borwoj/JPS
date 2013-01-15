package ast.binary;

import ast.Expression;
import edu.pjwstk.jps.ast.binary.IMinusSetExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class MinusSetExpression extends BinaryExpression implements
		IMinusSetExpression {

	public MinusSetExpression(Expression expLeft, Expression expRight) {
		super(expLeft, expRight);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitMinusSetExpression(this);

	}

}
