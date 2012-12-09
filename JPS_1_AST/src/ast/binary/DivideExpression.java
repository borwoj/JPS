package ast.binary;

import ast.Expression;
import edu.pjwstk.jps.ast.binary.IDivideExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class DivideExpression extends BinaryExpression implements
		IDivideExpression {

	public DivideExpression(Expression expLeft, Expression expRight) {
		super(expLeft, expRight);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitDivideExpression(this);

	}

}
