package ast.unary;
import ast.Expression;
import edu.pjwstk.jps.ast.unary.ISumExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;


public class SumExpression extends UnaryExpression implements
			ISumExpression {

	public SumExpression(Expression innerExp) {
		super(innerExp);
	}
	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitSumExpression(this);

	}
}
