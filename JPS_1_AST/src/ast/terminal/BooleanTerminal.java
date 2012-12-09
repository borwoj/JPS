package ast.terminal;

import edu.pjwstk.jps.ast.terminal.IBooleanTerminal;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class BooleanTerminal extends TerminalExpression<Boolean> implements
		IBooleanTerminal {

	public BooleanTerminal(Boolean value) {
		super(value);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitBooleanTerminal(this);
	}

}
