import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.IUnaryExpression;

public abstract class UnaryExpression extends Expression implements
		IUnaryExpression {

	public UnaryExpression(Expression innerExp) {

	}

	@Override
	public IExpression getInnerExpression() {
		// TODO Auto-generated method stub
		return null;
	}

}
