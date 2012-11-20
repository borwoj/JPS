package ast.binary;

import ast.Expression;
import edu.pjwstk.jps.ast.binary.IIntersectExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class IntersectExpression extends BinaryExpression implements
		IIntersectExpression {

	public IntersectExpression(Expression expLeft, Expression expRight) {
		super(expLeft, expRight);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor visitor) {
		// TODO Auto-generated method stub

	}

}
