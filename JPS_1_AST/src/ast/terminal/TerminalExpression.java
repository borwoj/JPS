package ast.terminal;

import ast.Expression;
import edu.pjwstk.jps.ast.terminal.ITerminalExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public abstract class TerminalExpression<T> extends Expression implements
		ITerminalExpression<T> {

	T value;

	public TerminalExpression(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public void accept(ASTVisitor visitor) {

	}
}
