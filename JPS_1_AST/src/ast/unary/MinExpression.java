package ast.unary;

import ast.Expression;
import edu.pjwstk.jps.ast.unary.IMinExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class MinExpression extends UnaryExpression implements IMinExpression {

	public MinExpression(Expression innerExp) {
		super(innerExp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor visitor) {
		// TODO Auto-generated method stub

	}

}
