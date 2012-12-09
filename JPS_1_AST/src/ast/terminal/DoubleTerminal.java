package ast.terminal;

import operatory.Interpreter;
import edu.pjwstk.jps.ast.terminal.IDoubleTerminal;

public class DoubleTerminal extends TerminalExpression<Double> implements
		IDoubleTerminal {

	public DoubleTerminal(Double value) {
		super(value);
	}

	@Override
	public void accept(Interpreter t) {
		t.visitDoubleTerminal(this);
	}

}
