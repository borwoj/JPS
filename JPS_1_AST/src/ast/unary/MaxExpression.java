package ast.unary;
import ast.Expression;
import edu.pjwstk.jps.ast.unary.IMaxExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;


public class MaxExpression extends UnaryExpression implements
			IMaxExpression {

	public MaxExpression(Expression innerExp) {
		super(innerExp);
	}
	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitMaxExpression(this);

	}
}
