package ast.unary;
import ast.Expression;
import edu.pjwstk.jps.ast.unary.ICountExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;


public class CountExpression extends UnaryExpression implements
		ICountExpression {

	public CountExpression(Expression innerExp) {
		super(innerExp);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitCountExpression(this);

	}
}
