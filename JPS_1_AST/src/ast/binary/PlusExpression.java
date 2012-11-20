package ast.binary;

import ast.Expression;
import edu.pjwstk.jps.ast.binary.IPlusExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class PlusExpression extends BinaryExpression implements IPlusExpression {

	public PlusExpression(Expression expLeft, Expression expRight) {
		super(expLeft, expRight);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		// TODO Auto-generated method stub

	}

}
