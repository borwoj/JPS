import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IBinaryExpression;

public abstract class BinaryExpression extends Expression implements
		IBinaryExpression {

	public BinaryExpression(Expression expLeft, Expression expRight) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public IExpression getLeftExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IExpression getRightExpression() {
		// TODO Auto-generated method stub
		return null;
	}

}
