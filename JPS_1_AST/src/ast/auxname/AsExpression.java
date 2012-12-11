package ast.auxname;

import ast.Expression;
import edu.pjwstk.jps.ast.auxname.IAsExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class AsExpression extends AuxiliaryNameExpression implements
		IAsExpression {

	public AsExpression(Expression innerExp, String auxiliaryName) {
		super(innerExp, auxiliaryName);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitAsExpression(this);

	}

}
