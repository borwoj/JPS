package ast.unary;

import ast.Expression;
import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.IUnaryExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public abstract class UnaryExpression extends Expression implements
		IUnaryExpression {
	Expression innerExp;

	public UnaryExpression(Expression innerExp) {
		this.innerExp = innerExp;
	}

	@Override
	public IExpression getInnerExpression() {
		return innerExp;
	}

}
