package ast.terminal;

import edu.pjwstk.jps.ast.terminal.IDoubleTerminal;

public class DoubleTerminal extends TerminalExpression<Double> implements IDoubleTerminal{

	public DoubleTerminal(Double value) {
		super(value);
	}
	
}
