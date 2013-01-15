package ast.binary;

import ast.Expression;
import edu.pjwstk.jps.ast.binary.ICommaExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class CommaExpression extends BinaryExpression implements
		ICommaExpression {

	public CommaExpression(Expression expLeft, Expression expRight) {
		super(expLeft, expRight);
	}
	
	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitCommaExpression(this);

	}

}
