package ast.unary;

import ast.Expression;
import edu.pjwstk.jps.ast.unary.IStructExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class StructExpression extends UnaryExpression implements
		IStructExpression {

	public StructExpression(Expression innerExp) {
		super(innerExp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor visitor) {
		// TODO Auto-generated method stub

	}

}
