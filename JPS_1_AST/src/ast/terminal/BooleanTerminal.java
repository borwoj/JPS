package ast.terminal;

import operatory.Interpreter;
import edu.pjwstk.jps.ast.terminal.IBooleanTerminal;

public class BooleanTerminal extends TerminalExpression<Boolean> implements IBooleanTerminal {

	public BooleanTerminal(Boolean value) {
		super(value);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void accept(Interpreter t) {
		t.visitBooleanTerminal(this);
	}

}
