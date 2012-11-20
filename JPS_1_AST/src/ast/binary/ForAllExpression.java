package ast.binary;

import ast.Expression;
import edu.pjwstk.jps.ast.binary.IForAllExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class ForAllExpression extends BinaryExpression implements
		IForAllExpression {

	public ForAllExpression(Expression expLeft, Expression expRight) {
		super(expLeft, expRight);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		// TODO Auto-generated method stub

	}

}
