package ast.unary;
import ast.Expression;
import edu.pjwstk.jps.ast.unary.IMaxExpression;


public class MaxExpression extends UnaryExpression implements
			IMaxExpression {

	public MaxExpression(Expression innerExp) {
		super(innerExp);
	}

}
