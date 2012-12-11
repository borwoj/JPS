package ast.unary;

import ast.Expression;
import edu.pjwstk.jps.ast.unary.IAvgExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class AvgExpression extends UnaryExpression implements IAvgExpression {

	public AvgExpression(Expression innerExp) {
		super(innerExp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitAvgExpression(this);

	}

}
