package ast.binary;
import ast.Expression;
import edu.pjwstk.jps.ast.binary.IEqualsExpression;


public class EqualsExpression extends BinaryExpression implements
		IEqualsExpression {

	public EqualsExpression(Expression expLeft, Expression expRight) {
		super(expLeft, expRight);
		// TODO Auto-generated constructor stub
	}

}
