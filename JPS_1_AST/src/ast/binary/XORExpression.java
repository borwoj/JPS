package ast.binary;

import ast.Expression;
import edu.pjwstk.jps.ast.binary.IXORExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class XORExpression extends BinaryExpression implements IXORExpression {

	public XORExpression(Expression expLeft, Expression expRight) {
		super(expLeft, expRight);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitXORExpression(this);

	}

}
