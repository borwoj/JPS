package ast.binary;

import ast.Expression;
import edu.pjwstk.jps.ast.binary.IJoinExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class JoinExpression extends BinaryExpression implements IJoinExpression {

	public JoinExpression(Expression expLeft, Expression expRight) {
		super(expLeft, expRight);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitJoinExpression(this);

	}

}
