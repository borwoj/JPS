package ast.binary;

import ast.Expression;
import edu.pjwstk.jps.ast.binary.IModuloExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class ModuloExpression extends BinaryExpression implements
		IModuloExpression {

	public ModuloExpression(Expression expLeft, Expression expRight) {
		super(expLeft, expRight);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitModuloExpression(this);

	}

}
