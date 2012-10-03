package ast.terminal;
import edu.pjwstk.jps.ast.terminal.INameTerminal;

public class NameTerminal extends TerminalExpression implements INameTerminal {

	public NameTerminal(String name) {
		super(name);
	}

	@Override
	public String getName() {
		return (String) value;
	}

}
