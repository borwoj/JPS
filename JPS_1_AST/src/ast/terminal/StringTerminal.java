package ast.terminal;

import edu.pjwstk.jps.ast.terminal.IStringTerminal;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class StringTerminal extends TerminalExpression<String> implements
		IStringTerminal {

	public StringTerminal(String value) {
		super(value);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitStringTerminal(this);
	}

}
