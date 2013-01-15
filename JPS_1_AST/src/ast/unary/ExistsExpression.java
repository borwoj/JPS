package ast.unary;

import ast.Expression;
import edu.pjwstk.jps.ast.unary.IExistsExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class ExistsExpression extends UnaryExpression implements
		IExistsExpression {

	public ExistsExpression(Expression innerExp) {
		super(innerExp);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitExistsExpression(this);

	}

}
