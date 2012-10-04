package ast.terminal;
import edu.pjwstk.jps.ast.terminal.IStringTerminal;

public class StringTerminal extends TerminalExpression<String> implements IStringTerminal {

	public StringTerminal(String value) {
		super(value);
	}

	@Override
	public String getValue() {
		return (String) value;
	}

}
