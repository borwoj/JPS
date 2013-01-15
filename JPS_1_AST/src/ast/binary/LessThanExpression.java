package ast.binary;

import ast.Expression;
import edu.pjwstk.jps.ast.binary.ILessThanExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class LessThanExpression extends BinaryExpression implements
		ILessThanExpression {

	public LessThanExpression(Expression expLeft, Expression expRight) {
		super(expLeft, expRight);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitLessThanExpression(this);

	}

}
