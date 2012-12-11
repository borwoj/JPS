package ast.auxname;

import ast.Expression;
import edu.pjwstk.jps.ast.auxname.IGroupAsExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class GroupAsExpression extends AuxiliaryNameExpression implements
		IGroupAsExpression {

	public GroupAsExpression(Expression innerExp, String auxiliaryName) {
		super(innerExp, auxiliaryName);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitGroupAsExpression(this);

	}

}
