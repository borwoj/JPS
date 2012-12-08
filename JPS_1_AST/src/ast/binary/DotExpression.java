package ast.binary;

import ast.Expression;
import edu.pjwstk.jps.ast.binary.IDotExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class DotExpression extends BinaryExpression implements IDotExpression {

	public DotExpression(Expression expLeft, Expression expRight) {
		super(expLeft, expRight);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitDotExpression(this);

	}

}
