package ast.unary;

import ast.Expression;
import edu.pjwstk.jps.ast.unary.INotExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class NotExpression extends UnaryExpression implements INotExpression {

	public NotExpression(Expression innerExp) {
		super(innerExp);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitNotExpression(this);

	}
}
