package ast.binary;

import ast.Expression;
import edu.pjwstk.jps.ast.binary.IUnionExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class UnionExpression extends BinaryExpression implements
		IUnionExpression {

	public UnionExpression(Expression expLeft, Expression expRight) {
		super(expLeft, expRight);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor visitor) {
		// TODO Auto-generated method stub

	}

}
