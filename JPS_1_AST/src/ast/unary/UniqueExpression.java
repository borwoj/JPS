package ast.unary;

import ast.Expression;
import edu.pjwstk.jps.ast.unary.IUniqueExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class UniqueExpression extends UnaryExpression implements
		IUniqueExpression {

	public UniqueExpression(Expression innerExp) {
		super(innerExp);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitUniqueExpression(this);

	}

}
