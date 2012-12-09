package ast.terminal;

import operatory.Interpreter;
import edu.pjwstk.jps.ast.terminal.IIntegerTerminal;

public class IntegerTerminal extends TerminalExpression<Integer> implements
		IIntegerTerminal {

	public IntegerTerminal(Integer value) {
		super(value);
	}

	@Override
	public void accept(Interpreter t) {
		t.visitIntegerTerminal(this);
	}

}
