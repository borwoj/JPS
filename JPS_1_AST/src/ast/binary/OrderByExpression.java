package ast.binary;

import ast.Expression;
import edu.pjwstk.jps.ast.binary.IOrderByExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class OrderByExpression extends BinaryExpression implements
		IOrderByExpression {

	public OrderByExpression(Expression expLeft, Expression expRight) {
		super(expLeft, expRight);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor visitor) {
		// TODO Auto-generated method stub

	}

}
