package ast.binary;

import ast.Expression;
import edu.pjwstk.jps.ast.binary.IWhereExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class WhereExpression extends BinaryExpression implements
		IWhereExpression {

	public WhereExpression(Expression expLeft, Expression expRight) {
		super(expLeft, expRight);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitWhereExpression(this);

	}

}
