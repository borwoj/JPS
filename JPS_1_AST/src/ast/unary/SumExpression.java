package ast.unary;
import ast.Expression;
import edu.pjwstk.jps.ast.unary.ISumExpression;


public class SumExpression extends UnaryExpression implements
			ISumExpression {

	public SumExpression(Expression innerExp) {
		super(innerExp);
	}

}
