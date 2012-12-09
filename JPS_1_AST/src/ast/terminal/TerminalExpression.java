package ast.terminal;

import operatory.Interpreter;
import ast.Expression;
import edu.pjwstk.jps.ast.terminal.ITerminalExpression;

public abstract class TerminalExpression<T> extends Expression implements
		ITerminalExpression<T> {

	T value;

	public TerminalExpression(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public void accept(Interpreter i) {

	}
}
