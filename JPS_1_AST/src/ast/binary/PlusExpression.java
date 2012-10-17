package ast.binary;
import ast.Expression;
import edu.pjwstk.jps.ast.binary.IPlusExpression;

public class PlusExpression extends BinaryExpression implements
			IPlusExpression {

	public PlusExpression(Expression expLeft, Expression expRight) {
		super(expLeft, expRight);
	}

}
