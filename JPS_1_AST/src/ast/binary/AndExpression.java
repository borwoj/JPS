package ast.binary;

import ast.Expression;
import edu.pjwstk.jps.ast.binary.IAndExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class AndExpression extends BinaryExpression implements IAndExpression {

	public AndExpression(Expression expLeft, Expression expRight) {
		super(expLeft, expRight);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitAndExpression(this);

	}

}
