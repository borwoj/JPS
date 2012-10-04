package ast.terminal;

import edu.pjwstk.jps.ast.terminal.IIntegerTerminal;

public class IntegerTerminal extends TerminalExpression<Integer> implements
		IIntegerTerminal {

	public IntegerTerminal(Integer value) {
		super(value);
	}

}
