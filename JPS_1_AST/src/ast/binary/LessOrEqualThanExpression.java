package ast.binary;

import ast.Expression;
import edu.pjwstk.jps.ast.binary.ILessOrEqualThanExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class LessOrEqualThanExpression extends BinaryExpression implements
		ILessOrEqualThanExpression {

	public LessOrEqualThanExpression(Expression expLeft, Expression expRight) {
		super(expLeft, expRight);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitLessOrEqualThanExpression(this);

	}

}
