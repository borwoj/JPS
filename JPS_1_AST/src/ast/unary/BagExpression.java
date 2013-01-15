package ast.unary;
import ast.Expression;
import edu.pjwstk.jps.ast.unary.IBagExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;


public class BagExpression extends UnaryExpression implements IBagExpression {

	public BagExpression(Expression innerExp) {
		super(innerExp);
	}
	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitBagExpression(this);

	}
}
