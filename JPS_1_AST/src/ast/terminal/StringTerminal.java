package ast.terminal;

import operatory.Interpreter;
import edu.pjwstk.jps.ast.terminal.IStringTerminal;

public class StringTerminal extends TerminalExpression<String> implements
		IStringTerminal {

	public StringTerminal(String value) {
		super(value);
	}

	@Override
	public void accept(Interpreter t) {
		t.visitStringTerminal(this);
	}

}
