package ast.binary;

import ast.Expression;
import edu.pjwstk.jps.ast.binary.ICloseByExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class CloseByExpression extends BinaryExpression implements ICloseByExpression {

	public CloseByExpression(Expression expLeft, Expression expRight) {
		super(expLeft, expRight);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitCloseByExpression(this);

	}

}
