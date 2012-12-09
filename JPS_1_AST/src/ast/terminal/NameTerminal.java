package ast.terminal;

import edu.pjwstk.jps.ast.terminal.INameTerminal;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class NameTerminal extends TerminalExpression implements INameTerminal {

	public NameTerminal(String name) {
		super(name);
	}

	public String getName() {
		return (String) value;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitNameTerminal(this);
	}

}
